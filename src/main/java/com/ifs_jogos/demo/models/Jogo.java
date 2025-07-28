package com.ifs_jogos.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "arbitro_id", nullable = false)
    private Usuario arbitro;

    @ManyToOne
    @JoinColumn(name = "equipe_a_id", nullable = false)
    private Equipe equipeA;

    @ManyToOne
    @JoinColumn(name = "equipe_b_id", nullable = false)
    private Equipe equipeB;

    private Integer placarEquipeA;

    private Integer placarEquipeB;

    @Enumerated(EnumType.STRING)
    private JogoStatusEnum status;

    @Enumerated(EnumType.STRING)
    private FaseEnum fase;
}