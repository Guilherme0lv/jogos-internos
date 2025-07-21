package com.ifs_jogos.demo.services.equipe.form;

import com.ifs_jogos.demo.models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipeForm {

    private String nome;
    private Integer cursoId;
    private Integer esporteId;
    private Integer campusId;
    private Integer tecnicoId;
    private List<Integer> atletasId;

    public Equipe paraModel(Curso curso, Esporte esporte, Campus campus, Usuario tecnico) {
        return Equipe.builder()
                .nome(nome)
                .curso(curso)
                .esporte(esporte)
                .campus(campus)
                .tecnico(tecnico)
                .build();
    }
}
