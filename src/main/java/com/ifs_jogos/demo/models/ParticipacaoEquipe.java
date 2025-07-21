package com.ifs_jogos.demo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipacaoEquipe {

    @EmbeddedId
    private ParticipacaoEquipeId id = new ParticipacaoEquipeId();

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("equipeId")
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
}