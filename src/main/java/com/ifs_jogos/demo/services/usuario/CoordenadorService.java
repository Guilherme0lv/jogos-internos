package com.ifs_jogos.demo.services.usuario;

import com.ifs_jogos.demo.models.Curso;
import com.ifs_jogos.demo.models.Usuario;
import com.ifs_jogos.demo.models.UsuarioEnum;
import com.ifs_jogos.demo.repositories.CursoRepository;
import com.ifs_jogos.demo.repositories.UsuarioRepository;
import com.ifs_jogos.demo.services.usuario.dto.UsuarioDTO;
import com.ifs_jogos.demo.services.usuario.form.CoordenadorForm;
import com.ifs_jogos.demo.util.PasswordUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CoordenadorService {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Transactional
    public void createCoordenador(CoordenadorForm form) {
        if (usuarioRepository.existsByTipoUsuarioAndCurso_Nome(UsuarioEnum.COORDENADOR, form.getCursoNome())) {
            throw new RuntimeException("Já existe um coordenador nesse curso");
        }
        Curso curso = cursoRepository.findByNome(form.getCursoNome());
         if(curso==null) {
             throw new RuntimeException("Curso não encontrado");
         }

        Usuario coordenador = form.paraModel(curso);
        coordenador.setSenha(gerarSenha());

        String senhaHash = PasswordUtils.encrypt(coordenador.getSenha());
        coordenador.setSenha(senhaHash);
        usuarioRepository.save(coordenador);
    }

    @Transactional
    public UsuarioDTO definirTecnico(String usuarioMatricula) {
        Usuario usuario = usuarioRepository.findByMatricula(usuarioMatricula);
        if (usuario==null) {
            throw new RuntimeException("Usuario nao encontrado");
        }

        usuario.setTipoUsuario(UsuarioEnum.TECNICO);

        return UsuarioDTO.deModel(usuarioRepository.save(usuario));
    }

    private static String gerarSenha() {
        int senha = new Random().nextInt(900000) + 100000;

        StringBuilder sb = new StringBuilder();
        sb.append("coord");
        sb.append(senha);

        return sb.toString();
    }

    public String enviarSenhaPorEmail(String coordenadorMatricula) {
        Usuario coordenador = usuarioRepository.findByMatricula(coordenadorMatricula);
        if(coordenador==null){
             throw new RuntimeException("Coordenador não encontrado.");
        }

        if (coordenador.getTipoUsuario() != UsuarioEnum.COORDENADOR) {
            throw new RuntimeException("Usuario informado não é um coordenador");
        }

        String senhaDescriptograda = PasswordUtils.decrypt(coordenador.getSenha());

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("O seu login é: ").append(coordenador.getMatricula());
        mensagem.append(" e sua senha é: ").append(senhaDescriptograda);

        return mensagem.toString();
    }
}
