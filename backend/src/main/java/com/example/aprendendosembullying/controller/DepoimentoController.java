package com.example.aprendendosembullying.controller;

import com.example.aprendendosembullying.model.Depoimento;
import com.example.aprendendosembullying.repository.DepoimentoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depoimentos")
public class DepoimentoController {

    @Autowired
    private DepoimentoRepository depoimentoRepository;

    @GetMapping
    public List<Depoimento> listar() {
        return depoimentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Depoimento umDepoimento(
            @PathVariable Long id
    ) {
        return depoimentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Depoimento não encontrado"));
    }

    @PostMapping
    public Depoimento adicionar(
            @RequestBody
            @Valid
            CriarDepoimento criarDepoimento
    ) {
        var depoimento = new Depoimento();
        depoimento.setTexto(criarDepoimento.texto());

        return depoimentoRepository.save(depoimento);
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable Long id) {
        depoimentoRepository.deleteById(id);

        return "Deletado com sucesso";
    }

    @PutMapping("/{id}")
    public Depoimento atualizar(
            @PathVariable Long id,
            @RequestBody
            @Valid
            CriarDepoimento criarDepoimento
    ) {
        var depoimento = depoimentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Depoimento não encontrado"));
        depoimento.setTexto(criarDepoimento.texto());

        return depoimentoRepository.save(depoimento);
    }

}

record CriarDepoimento(
        @NotEmpty
        String texto
) {

}
