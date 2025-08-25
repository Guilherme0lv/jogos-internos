package com.ifs_jogos.demo.services.evento.form;

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
public class EventoForm {

    private String tipoEvento;
    private String dataInicio;
    private String dataFim;

    public Evento paraModel(LocalDate dataInicioFormatada,  LocalDate dataFimFormatada) {
        return Evento.builder()
                .tipoEvento(tipoEvento)
                .dataInicio(dataInicioFormatada)
                .dataFim(dataFimFormatada)
                .build();
    }
}
