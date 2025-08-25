package com.ifs_jogos.demo.services.eliminatorias.form;

import com.ifs_jogos.demo.models.FaseEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaseForm {

    private String nomeEsporte;
    private String faseAtual;

}
