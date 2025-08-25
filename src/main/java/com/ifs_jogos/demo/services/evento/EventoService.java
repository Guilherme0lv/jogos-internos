package com.ifs_jogos.demo.services.evento;


import com.ifs_jogos.demo.models.Evento;
import com.ifs_jogos.demo.repositories.EventoRepository;
import com.ifs_jogos.demo.services.evento.dto.EventoDTO;
import com.ifs_jogos.demo.services.evento.form.EventoForm;
import com.ifs_jogos.demo.util.DataUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    @Transactional
    public EventoDTO createEvento(EventoForm form) {
        LocalDate dataInicioFormatada = DataUtil.converterParaLocalDate(form.getDataInicio());
        LocalDate dataFimFormatada = DataUtil.converterParaLocalDate(form.getDataFim());
        if (dataInicioFormatada.isAfter(dataFimFormatada)) {
            throw new RuntimeException("Datas invalidas");
        }
        Evento evento = form.paraModel(dataInicioFormatada, dataFimFormatada);

        return EventoDTO.deModel(eventoRepository.save(evento));
    }

    public List<EventoDTO> getEventos() {
        List<Evento> eventos =  eventoRepository.findAll();

        List<EventoDTO > dtoList = new ArrayList<>();
        for (Evento e : eventos) {
            dtoList.add(EventoDTO.deModel(e));
        }
        return dtoList;
    }

    @Transactional
    public EventoDTO updateEvento(String tipoEvento, EventoForm form) {
        Evento evento = eventoRepository.findByTipoEvento(tipoEvento);
        if(evento==null) {
            throw new RuntimeException("Evento não encontrado.");
        }
        LocalDate dataInicioFormatada = DataUtil.converterParaLocalDate(form.getDataInicio());
        LocalDate dataFimFormatada = DataUtil.converterParaLocalDate(form.getDataFim());
        if (dataInicioFormatada.isAfter(dataFimFormatada)) {
            throw new RuntimeException("Datas invalidas");
        }
        if(form.getTipoEvento()!=null) {
            evento.setTipoEvento(form.getTipoEvento());
        }
        if(form.getDataInicio()!=null) {
            evento.setDataInicio(dataInicioFormatada);
        }
        if(form.getDataFim()!=null) {
            evento.setDataFim(dataFimFormatada);
        }

        eventoRepository.save(evento);
        return EventoDTO.deModel(evento);
    }

    @Transactional
    public void deleteEvento(String tipoEvento) {
        Evento evento = eventoRepository.findByTipoEvento(tipoEvento);
        if(evento==null) {
            throw new RuntimeException("Evento não encontrado.");
        }

        eventoRepository.delete(evento);
    }

}
