package com.ifs_jogos.demo.services.eliminatorias.dto;

import com.ifs_jogos.demo.models.FaseEnum;
import com.ifs_jogos.demo.models.Jogo;
import com.ifs_jogos.demo.services.jogo.dto.JogoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EliminatoriasDTO {

    private List<JogoDTO> jogos;

    public static EliminatoriasDTO deModel(List<JogoDTO> jogos) {
       return new EliminatoriasDTO(jogos);
    }

}
