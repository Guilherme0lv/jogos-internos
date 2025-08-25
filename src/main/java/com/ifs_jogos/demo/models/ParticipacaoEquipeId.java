package com.ifs_jogos.demo.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipacaoEquipeId implements Serializable {

    private Integer usuarioId;
    private Integer equipeId;

}