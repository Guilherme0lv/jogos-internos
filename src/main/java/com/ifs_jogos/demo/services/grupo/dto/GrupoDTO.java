package com.ifs_jogos.demo.services.grupo.dto;

import com.ifs_jogos.demo.models.Equipe;
import com.ifs_jogos.demo.models.Grupo;
import com.ifs_jogos.demo.models.Jogo;
import com.ifs_jogos.demo.services.equipe.dto.EquipeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoDTO {

    private String nome;
    private String nomeEsporte;
    private List<String> equipes;
    private List<String> jogos;

    public static GrupoDTO deModel(Grupo grupo) {

        return GrupoDTO.builder()
                .nome(grupo.getNome())
                .nomeEsporte(grupo.getEsporte().getNome())
                .equipes(Optional.ofNullable(grupo.getEquipes())
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(Equipe::getNome)
                            .toList())
                .jogos(formatarJogos(grupo.getJogos()))
                .build();
    }

    public static List<String> formatarJogos(List<Jogo> jogos) {

        List<String> jogosFormatados = new ArrayList<>(jogos.size());

        for (Jogo jogo : jogos) {
            StringBuilder sb = new StringBuilder();
            sb.append(jogo.getEquipeA().getNome())
                    .append(" x ")
                    .append(jogo.getEquipeB().getNome());

            jogosFormatados.add(sb.toString());
        }

        return jogosFormatados;
    }
}