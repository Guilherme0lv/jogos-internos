package com.ifs_jogos.demo.services.equipe.dto;

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
public class EquipeDTO {

    private String nome;
    private Integer pontos;
    private Integer vitorias;
    private Integer derrotas;
    private String cursoNome;
    private String esporteNome;
    private String campusNome;
    private String tecnicoMatricula;
    private List<String> atletasMatricula;

    public static EquipeDTO deModel(Equipe equipe) {
        return EquipeDTO.builder()
                .nome(equipe.getNome())
                .pontos(equipe.getPontos())
                .vitorias(equipe.getVitorias())
                .derrotas(equipe.getDerrotas())
                .cursoNome(equipe.getCurso().getNome())
                .esporteNome(equipe.getEsporte().getNome())
                .campusNome(equipe.getCampus().getNome())
                .tecnicoMatricula(equipe.getTecnico()!=null ? equipe.getTecnico().getMatricula() : "Não há" )
                .atletasMatricula(equipe.getAtletas()
                                .stream()
                                .map(a -> a.getUsuario().getMatricula())
                                .toList()
                )
                .build();
    }

    public static EquipeDTO deModelPost(Equipe equipe, List<ParticipacaoEquipe> participacoes) {
        return EquipeDTO.builder()
                .nome(equipe.getNome())
                .pontos(equipe.getPontos())
                .vitorias(equipe.getVitorias())
                .derrotas(equipe.getDerrotas())
                .cursoNome(equipe.getCurso().getNome())
                .esporteNome(equipe.getEsporte().getNome())
                .campusNome(equipe.getCampus().getNome())
                .tecnicoMatricula(equipe.getTecnico()!=null ? equipe.getTecnico().getMatricula() : "Não há" )
                .atletasMatricula(participacoes.stream()
                        .map(p -> p.getUsuario().getMatricula())
                        .toList()
                )
                .build();
    }

}
