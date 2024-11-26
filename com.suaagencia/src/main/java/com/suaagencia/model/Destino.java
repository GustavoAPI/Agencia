package com.suaagencia.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String localizacao;
    private String descricao;

    private double mediaAvaliacoes = 0;

    @ElementCollection
    private List<Integer> avaliacoes = new ArrayList<>();
}