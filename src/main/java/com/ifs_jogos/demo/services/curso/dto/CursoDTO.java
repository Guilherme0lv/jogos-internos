package com.ifs_jogos.demo.services.curso.dto;

import com.ifs_jogos.demo.models.Curso;
import com.ifs_jogos.demo.models.CursoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {

    private String nome;
    private CursoEnum tipoCurso;
    private String coordenador;

    public static CursoDTO deModel(Curso curso) {
        return CursoDTO.builder()
                .nome(curso.getNome())
                .tipoCurso(curso.getTipoCurso())
                .coordenador(curso.getCoordenador().getNomeCompleto())
                .build();
    }
}
