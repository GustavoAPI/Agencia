package com.suaagencia.repository;

import com.suaagencia.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Long> {
    List<Destino> findByNomeContainingIgnoreCase(String nome);
    List<Destino> findByLocalizacaoContainingIgnoreCase(String localizacao);
}
