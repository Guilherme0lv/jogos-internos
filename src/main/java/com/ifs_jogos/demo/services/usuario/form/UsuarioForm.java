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
public class UsuarioForm {

    private String matricula;
    private String senha;
    private String nomeCompleto;
    private String apelido;
    private String telefone;
    private UsuarioEnum tipoUsuario;
    private Integer cursoId;

    public Usuario paraModel(Curso curso) {
        return Usuario.builder()
                .matricula(matricula)
                .senha(senha)
                .nomeCompleto(nomeCompleto)
                .apelido(apelido)
                .telefone(telefone)
                .tipoUsuario(tipoUsuario)
                .curso(curso)
                .build();
    }
}
