package com.ifs_jogos.demo.services.equipe.dto;

import com.ifs_jogos.demo.models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO {

    private String nome;
    private Integer pontos;
    private Integer vitorias;
    private Integer derrotas;
    private Integer cursoId;
    private Integer esporteId;
    private Integer campusId;
    private Integer tecnicoId;
    private List<Integer> atletasId;

    public static EquipeDTO deModel(Equipe equipe) {
        return EquipeDTO.builder()
                .nome(equipe.getNome())
                .pontos(equipe.getPontos())
                .vitorias(equipe.getVitorias())
                .derrotas(equipe.getDerrotas())
                .cursoId(equipe.getCurso().getId())
                .esporteId(equipe.getEsporte().getId())
                .campusId(equipe.getCampus().getId())
                .tecnicoId(equipe.getTecnico()!=null ? equipe.getTecnico().getId() : -1 )
                .atletasId(
                        Optional.ofNullable(equipe.getAtletas())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(a -> a.getUsuario().getId())
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
                .cursoId(equipe.getCurso().getId())
                .esporteId(equipe.getEsporte().getId())
                .campusId(equipe.getCampus().getId())
                .tecnicoId(equipe.getTecnico()!=null ? equipe.getTecnico().getId() : -1 )
                .atletasId(participacoes.stream()
                        .map(p -> p.getUsuario().getId())
                        .toList()
                )
                .build();
    }

}
