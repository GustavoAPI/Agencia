
package com.suaagencia.service;

import com.suaagencia.model.Destino;
import com.suaagencia.repository.DestinoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service

public class DestinoService {

	@Autowired
    private DestinoRepository destinoRepository;
    private final DestinoRepository repository;

    public DestinoService(DestinoRepository repository) {
        this.repository = repository;
    }

    public Destino salvar(Destino destino) {
        return repository.save(destino);
    }

    public List<Destino> listarTodos() {
        return repository.findAll();
    }

    public List<Destino> pesquisarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Destino> pesquisarPorLocalizacao(String localizacao) {
        return repository.findByLocalizacaoContainingIgnoreCase(localizacao);
    }

    public Destino buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Destino não encontrado!"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Destino avaliarDestino(Long id, int nota) {
    	
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 10.");
        }

        Destino destino = destinoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Destino não encontrado."));

        // Adiciona a nova avaliação
        destino.getAvaliacoes().add(nota);

        // Recalcula a média
        double novaMedia = destino.getAvaliacoes().stream()
                                  .mapToInt(Integer::intValue)
                                  .average()
                                  .orElse(0.0);
        destino.setMediaAvaliacoes(novaMedia);

        // Salva o destino atualizado no banco
        return destinoRepository.save(destino);
    }
    
    public Destino atualizarDestino(Long id, Destino destinoAtualizado) {
        // Verifica se o destino existe no banco
        Destino destinoExistente = destinoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Destino não encontrado."));

        // Atualiza os campos do destino
        if (destinoAtualizado.getNome() != null) {
            destinoExistente.setNome(destinoAtualizado.getNome());
        }
        if (destinoAtualizado.getLocalizacao() != null) {
            destinoExistente.setLocalizacao(destinoAtualizado.getLocalizacao());
        }
        if (destinoAtualizado.getDescricao() != null) {
            destinoExistente.setDescricao(destinoAtualizado.getDescricao());
        }

        // Salva e retorna o destino atualizado
        return destinoRepository.save(destinoExistente);
    }

}
