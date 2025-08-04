package com.ifs_jogos.demo.services.esporte.dto;


import com.ifs_jogos.demo.models.Esporte;

import com.ifs_jogos.demo.models.EsporteEnum;
import com.ifs_jogos.demo.services.esporte.form.EsporteForm;
import com.ifs_jogos.demo.services.grupo.dto.GrupoDTO;
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
public class EsporteDTO {

    private String nome;
    private String tipoEvento;
    private Integer minAtletas;
    private Integer maxAtletas;
    private String campeao;

    public static EsporteDTO deModel(Esporte esporte) {
        String campeao = esporte.getCampeao()!=null ? esporte.getCampeao().getNome() : "Ainda não há.";
        return EsporteDTO.builder()
                .nome(esporte.getNome())
                .tipoEvento(esporte.getEvento().getTipoEvento())
                .minAtletas(esporte.getMinAtletas())
                .maxAtletas(esporte.getMaxAtletas())
                .campeao(campeao)
                .build();
    }
}
