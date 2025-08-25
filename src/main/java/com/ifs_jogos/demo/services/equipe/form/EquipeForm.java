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
    private String cursoNome;
    private String esporteNome;
    private String campusNome;
    private String tecnicoMatricula;
    private List<String> atletasMatricula;

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
