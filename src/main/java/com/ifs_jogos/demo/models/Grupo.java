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
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "esporte_id")
    private Esporte esporte;

    @Column(nullable = true)
    @OneToMany(mappedBy = "grupo")
    private List<Equipe> equipes = new ArrayList<>();

    @Column(nullable = true)
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Jogo> jogos;
}
