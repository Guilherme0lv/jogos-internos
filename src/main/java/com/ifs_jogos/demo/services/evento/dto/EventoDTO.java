package com.ifs_jogos.demo.services.evento.dto;


import com.ifs_jogos.demo.models.Evento;
import com.ifs_jogos.demo.util.DataUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private String tipoEvento;
    private String dataInicio;
    private String dataFim;

    public static EventoDTO deModel(Evento evento) {
        return EventoDTO.builder()
                .tipoEvento(evento.getTipoEvento())
                .dataInicio(DataUtil.formatarLocalDate(evento.getDataInicio()))
                .dataFim(DataUtil.formatarLocalDate(evento.getDataFim()))
                .build();
    }


}
