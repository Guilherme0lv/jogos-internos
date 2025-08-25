package com.ifs_jogos.demo.services.esporte.dto;


import com.ifs_jogos.demo.models.Esporte;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsporteDTO {

    private String nome;
    private String evento;
    private Integer minAtletas;
    private Integer maxAtletas;
    private String campeao;

    public static EsporteDTO deModel(Esporte esporte) {
        String campeao = esporte.getCampeao()!=null ? esporte.getCampeao().getNome() : "Ainda não há.";
        return EsporteDTO.builder()
                .nome(esporte.getNome())
                .evento(esporte.getEvento().getTipoEvento())
                .minAtletas(esporte.getMinAtletas())
                .maxAtletas(esporte.getMaxAtletas())
                .campeao(campeao)
                .build();
    }
}
