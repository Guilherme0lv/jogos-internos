package com.ifs_jogos.demo.services.jogo.dto;

import com.ifs_jogos.demo.models.Equipe;
import com.ifs_jogos.demo.models.Grupo;
import com.ifs_jogos.demo.models.Jogo;
import com.ifs_jogos.demo.models.Usuario;
import com.ifs_jogos.demo.util.DataUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JogoDTO {

    private Integer id;
    private String dataHora;
    private String arbitro;
    private String nomeEquipeA;
    private String nomeEquipeB;
    private Integer placarEquipeA;
    private Integer placarEquipeB;
    private String status;

    public static JogoDTO deModel(Jogo jogo) {
        return JogoDTO.builder()
                .id(jogo.getId())
                .dataHora(DataUtil.formatarLocalDateTime(jogo.getDataHora()))
                .arbitro(jogo.getArbitro().getNomeCompleto())
                .nomeEquipeA(jogo.getEquipeA().getNome())
                .nomeEquipeB(jogo.getEquipeB().getNome())
                .placarEquipeA(jogo.getPlacarEquipeA())
                .placarEquipeB(jogo.getPlacarEquipeB())
                .status(jogo.getStatus().paraString())
                .build();
    }


}
