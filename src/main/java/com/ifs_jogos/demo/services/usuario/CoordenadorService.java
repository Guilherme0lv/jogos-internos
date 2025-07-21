package com.ifs_jogos.demo.services.usuario;

import com.ifs_jogos.demo.models.Usuario;
import com.ifs_jogos.demo.models.UsuarioEnum;
import com.ifs_jogos.demo.repositories.UsuarioRepository;
import com.ifs_jogos.demo.services.usuario.dto.UsuarioDTO;
import com.ifs_jogos.demo.services.usuario.form.CoordenadorForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CoordenadorService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioDTO createCoordenador(CoordenadorForm form) {
        Usuario coordenador = form.paraModel();
        coordenador.setSenha(gerarSenha());
        usuarioRepository.save(coordenador);

        return UsuarioDTO.deModel(coordenador);
    }

    @Transactional
    public UsuarioDTO definirTecnico(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow( () -> new RuntimeException("Usuario não encontrado."));

        usuario.setTipoUsuario(UsuarioEnum.TECNICO);

        return UsuarioDTO.deModel(usuarioRepository.save(usuario));
    }

    private static String gerarSenha() {
        int senha = new Random().nextInt(900000) + 100000;
        return String.valueOf(senha);
    }

    public String enviarSenhaPorEmail(Integer coordenadorId) {
        Usuario coordenador = usuarioRepository.findById(coordenadorId)
                .orElseThrow( () -> new RuntimeException("Coordenador não encontrado."));

        if (coordenador.getTipoUsuario() != UsuarioEnum.COORDENADOR) {
            throw new RuntimeException("Usuario informado não é um coordenador");
        }

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("O seu login é: ").append(coordenador.getMatricula());
        mensagem.append(" e sua senha é: ").append(coordenador.getSenha());

        return mensagem.toString();
    }
}
