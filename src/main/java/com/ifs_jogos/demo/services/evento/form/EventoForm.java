package com.ifs_jogos.demo.services.evento.form;

import com.ifs_jogos.demo.models.Evento;
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
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Evento paraModel() {
        return Evento.builder()
                .tipoEvento(tipoEvento)
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .build();
    }
}
