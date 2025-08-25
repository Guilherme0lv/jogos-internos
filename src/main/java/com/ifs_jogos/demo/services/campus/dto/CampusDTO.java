package com.ifs_jogos.demo.services.campus.dto;

import com.ifs_jogos.demo.models.Campus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampusDTO {
    private String nome;
    private String cidade;

    public static CampusDTO deModel(Campus campus) {
        return CampusDTO.builder()

                .nome(campus.getNome())
                .cidade(campus.getCidade())
                .build();
    }
}
