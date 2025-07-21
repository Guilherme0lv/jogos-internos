package com.ifs_jogos.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Esporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "nome_esporte")
    private String nome;

    @Column(nullable = false)
    private Integer minAtletas;

    @Column(nullable = false)
    private Integer maxAtletas;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "campeao_id")
    private Equipe campeao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @OneToMany(mappedBy = "esporte", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> grupos = new ArrayList<>();


}
