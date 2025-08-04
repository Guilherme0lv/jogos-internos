package com.ifs_jogos.demo.services.usuario.form;

import com.ifs_jogos.demo.models.Curso;
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
public class CoordenadorForm {

    private String matricula;
    private String nomeCompleto;
    private Integer cursoId;
    private String apelido;
    private String telefone;

    public Usuario paraModel(Curso curso) {
        return Usuario.builder()
                .matricula(matricula)
                .nomeCompleto(nomeCompleto)
                .apelido(apelido)
                .telefone(telefone)
                .tipoUsuario(UsuarioEnum.COORDENADOR)
                .curso(curso)
                .build();
    }

}
