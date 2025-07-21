package com.ifs_jogos.demo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "nome_curso")
    private String nome;

    @Column(nullable = false, name = "tipo_curso")
    @Enumerated(EnumType.STRING)
    private CursoEnum tipoCurso;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordenador_id")
    private Usuario coordenador;


}
