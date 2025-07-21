package com.ifs_jogos.demo.services.jogo.form;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlacarForm {
    private Integer idJogo;
    private Integer placarA;
    private Integer placarB;

}