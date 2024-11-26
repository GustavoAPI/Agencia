package com.suaagencia.controller;

import com.suaagencia.model.Destino;
import com.suaagencia.service.DestinoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/destinos")
public class DestinoController {

    private final DestinoService service;
	private DestinoService destinoService;

    public DestinoController(DestinoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Destino> cadastrarDestino(@RequestBody Destino destino) {
        return ResponseEntity.ok(service.salvar(destino));
    }

    @GetMapping
    public ResponseEntity<List<Destino>> listarDestinos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Destino>> pesquisarDestinos(@RequestParam(required = false) String nome,
                                                           @RequestParam(required = false) String localizacao) {
        if (nome != null) return ResponseEntity.ok(service.pesquisarPorNome(nome));
        if (localizacao != null) return ResponseEntity.ok(service.pesquisarPorLocalizacao(localizacao));
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destino> detalhes(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<Void> avaliarDestino(@PathVariable Long id, @RequestParam int nota) {
        service.avaliarDestino(id, nota);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDestino(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Destino> atualizarDestino(
            @PathVariable Long id,
            @RequestBody Destino destinoAtualizado) {
        try {
            Destino destino = destinoService.atualizarDestino(id, destinoAtualizado);
            return ResponseEntity.ok(destino);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
