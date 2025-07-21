package com.ifs_jogos.demo.services.usuario.dto;

import com.ifs_jogos.demo.models.Usuario;
import com.ifs_jogos.demo.models.UsuarioEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String matricula;
    private String nomeCompleto;
    private String apelido;
    private String telefone;
    private UsuarioEnum tipoUsuario;

    public static UsuarioDTO deModel(Usuario usuario) {
        return UsuarioDTO.builder()
                .matricula(usuario.getMatricula())
                .nomeCompleto(usuario.getNomeCompleto())
                .apelido(usuario.getApelido())
                .telefone(usuario.getTelefone())
                .tipoUsuario(usuario.getTipoUsuario())
                .build();
    }

}
