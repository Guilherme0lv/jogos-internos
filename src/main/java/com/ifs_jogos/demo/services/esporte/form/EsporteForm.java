package com.ifs_jogos.demo.services.esporte.form;


import com.ifs_jogos.demo.models.Esporte;
import com.ifs_jogos.demo.models.EsporteEnum;
import com.ifs_jogos.demo.models.Evento;
import com.ifs_jogos.demo.services.esporte.dto.EsporteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsporteForm {

    private String nome;
    private String evento;
    private Integer minAtletas;
    private Integer maxAtletas;

    public Esporte paraModel(Evento evento) {
        return Esporte.builder()
                .nome(nome)
                .evento(evento)
                .minAtletas(minAtletas)
                .maxAtletas(maxAtletas)
                .build();
    }
}
