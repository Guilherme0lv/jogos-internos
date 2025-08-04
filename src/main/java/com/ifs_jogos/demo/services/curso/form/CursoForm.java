package com.ifs_jogos.demo.services.curso.form;

import com.ifs_jogos.demo.models.Curso;
import com.ifs_jogos.demo.models.CursoEnum;
import com.ifs_jogos.demo.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CursoForm {

    private String nome;
    private CursoEnum tipoCurso;

    public Curso paraModel() {
        return Curso.builder()
                .nome(nome)
                .tipoCurso(tipoCurso)
                .build();
    }
}
