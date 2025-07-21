package com.ifs_jogos.demo.services.grupo.dto;

import com.ifs_jogos.demo.models.Equipe;
import com.ifs_jogos.demo.models.Grupo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoDTO {

    private Integer posicao;
    private String nomeEquipe;
    private Integer pontos;
    private Integer vitorias;
    private Integer derrotas;

    public static List<ClassificacaoDTO> mostrarClassificacao(List<Equipe> equipes) {
        List<ClassificacaoDTO> classificacaoList = new ArrayList<>();
        for (Equipe e : equipes) {
            ClassificacaoDTO classificacao = new ClassificacaoDTO();
            classificacao.setPosicao(equipes.indexOf(e) + 1);
            classificacao.setNomeEquipe(e.getNome());
            classificacao.setPontos(e.getPontos());
            classificacao.setVitorias(e.getVitorias());
            classificacao.setDerrotas(e.getDerrotas());
            classificacaoList.add(classificacao);
        }
        return classificacaoList;
    }


}
