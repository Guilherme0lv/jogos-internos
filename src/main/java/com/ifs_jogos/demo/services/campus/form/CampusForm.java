package com.ifs_jogos.demo.services.campus.form;


import com.ifs_jogos.demo.models.Campus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampusForm {

    private String nome;
    private String cidade;

    public Campus paraModel() {
        return Campus.builder()
                .nome(this.nome)
                .cidade(this.cidade)
                .build();
    }
}

