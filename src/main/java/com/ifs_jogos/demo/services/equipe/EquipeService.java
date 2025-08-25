package com.ifs_jogos.demo.services.equipe;

import com.ifs_jogos.demo.models.*;
import com.ifs_jogos.demo.repositories.*;
import com.ifs_jogos.demo.services.equipe.dto.EquipeDTO;
import com.ifs_jogos.demo.services.equipe.form.EquipeForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final CursoRepository cursoRepository;
    private final CampusRepository campusRepository;
    private final EsporteRepository esporteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ParticipacaoEquipeRepository participacaoEquipeRepository;

    @Transactional
    public EquipeDTO createEquipe(EquipeForm form) {
        Curso curso = cursoRepository.findByNome(form.getCursoNome());
        if (curso==null) {
            throw new RuntimeException("Curso não encontrado.");
        }
        Esporte esporte = esporteRepository.findByNome(form.getEsporteNome());
        if (esporte==null) {
            throw new RuntimeException("Esporte não encontrado.");
        }
        Campus campus = campusRepository.findByNome(form.getCampusNome());
        if (campus==null) {
            throw new RuntimeException("Campus não encontrado.");
        }
        Usuario tecnico = usuarioRepository.findByMatricula(form.getTecnicoMatricula());
        if (tecnico==null) {
            throw new RuntimeException("Tecnico não encontrado.");
        }

        if (equipeRepository.existsByCursoAndEsporte(curso, esporte)) {
            throw new RuntimeException("Já existe equipe desse curso cadastrada.");
        }

        if (form.getAtletasMatricula().size() < esporte.getMinAtletas() || form.getAtletasMatricula().size() > esporte.getMaxAtletas()) {
            throw new RuntimeException("Tamanho invalido da equipe");
        }

        if (!curso.getTipoCurso().paraString().equals(esporte.getEvento().getTipoEvento())) {
            throw new RuntimeException("O curso inserido não é permitido nesse esporte!");
        }

        if (equipeRepository.existsByTecnicoAndEsporte(tecnico, esporte)) {
            throw new RuntimeException("Este técnico já está vinculado a uma equipe nesse esporte.");
        }

        Equipe equipe = form.paraModel(curso, esporte, campus, tecnico);

        equipe.setVitorias(0);
        equipe.setDerrotas(0);
        equipe.setPontos(0);
        equipeRepository.save(equipe);

        List<ParticipacaoEquipe> participacoes = addUsuariosNaEquipe(equipe, form.getAtletasMatricula());

        return EquipeDTO.deModelPost(equipe, participacoes);
    }

    @Transactional
    private List<ParticipacaoEquipe> addUsuariosNaEquipe(Equipe equipe, List<String> atletasMatricula) {
        List<Usuario> usuarios = usuarioRepository.findAllByMatriculaIn(atletasMatricula);

        for (Usuario u : usuarios) {
          if (u.getCurso() != equipe.getCurso()) {
              throw new RuntimeException("Usuario não pertence ao curso da equipe.");
          }
        }

        if (usuarios.size() != atletasMatricula.size()) {
            throw new RuntimeException("Um ou mais usuários não foram encontrados");
        }

        List<ParticipacaoEquipe> participacaoList = usuarios.stream()
                .map(usuario -> {
                    ParticipacaoEquipeId id = new ParticipacaoEquipeId(usuario.getId(), equipe.getId());
                    return new ParticipacaoEquipe(id, usuario, equipe);
                })
                .toList();

        return participacaoEquipeRepository.saveAll(participacaoList);
    }


    public List<EquipeDTO> getEquipes() {
        List<Equipe> equipeList = equipeRepository.findAll();
        List<EquipeDTO> dtoList = new ArrayList<>();
        for (Equipe e : equipeList) {
            dtoList.add(EquipeDTO.deModel(e));
        }
        return dtoList;
    }


    public EquipeDTO findByNome(String nome) {
        Equipe equipe = equipeRepository.findByNome(nome);
        return EquipeDTO.deModel(equipe);
    }


    public List<EquipeDTO> findByCurso(Integer cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

        List<Equipe> equipes = equipeRepository.findByCurso(curso);
        List<EquipeDTO> dtoList = new ArrayList<>();
        for (Equipe e: equipes) {
            dtoList.add(EquipeDTO.deModel(e));
        }
        return dtoList;
    }


    public List<EquipeDTO> findByEsporte(String nomeEsporte) {
        Esporte esporte = esporteRepository.findByNome(nomeEsporte);
        if(esporte==null) {
            throw new RuntimeException("Esporte não encontrado.");
        }

        List<Equipe> equipes = equipeRepository.findByEsporte(esporte);
        List<EquipeDTO> dtoList = new ArrayList<>();
        for (Equipe e: equipes) {
            dtoList.add(EquipeDTO.deModel(e));
        }
        return dtoList;
    }


    public List<EquipeDTO> findByCampus(Integer campusId) {
        Campus campus = campusRepository.findById(campusId)
                .orElseThrow(() -> new RuntimeException("Campus não encontrado."));
        List<Equipe> equipes = equipeRepository.findByCampus(campus);
        List<EquipeDTO> dtoList = new ArrayList<>();
        for (Equipe e: equipes) {
            dtoList.add(EquipeDTO.deModel(e));
        }
        return dtoList;
    }

    @Transactional
    public EquipeDTO updateEquipe(String nomeEquipe, EquipeForm form) {

        Equipe equipe = equipeRepository.findByNome(nomeEquipe);
        if (equipe==null) {
            throw new RuntimeException("Equipe não encontrada.");
        }

        if (form.getCursoNome()!=null) {
            Curso curso = cursoRepository.findByNome(form.getCursoNome());
            if (curso==null) {
                throw new RuntimeException("Curso não encontrado.");
            }
            equipe.setCurso(curso);
        }

        if (form.getEsporteNome()!=null) {
            Esporte esporte = esporteRepository.findByNome(form.getEsporteNome());
            if (esporte==null) {
                throw new RuntimeException("Esporte não encontrado.");
            }
            if (form.getAtletasMatricula().size() < esporte.getMinAtletas() || form.getAtletasMatricula().size() > esporte.getMaxAtletas()) {
                throw new RuntimeException("Tamanho inválido da equipe");
            }
            equipe.setEsporte(esporte);
        }
        if (form.getCampusNome()!=null) {
            Campus campus = campusRepository.findByNome(form.getCampusNome());
            if (campus==null) {
                throw new RuntimeException("Campus não encontrado.");
            }
            equipe.setCampus(campus);
        }
        if (form.getTecnicoMatricula()!=null) {
            Usuario tecnico = usuarioRepository.findByMatricula(form.getTecnicoMatricula());
            if (tecnico==null) {
                throw new RuntimeException("Tecnico não encontrado.");
            }
            equipe.setTecnico(tecnico);
        }

        if (!equipe.getCurso().getTipoCurso().paraString().equals(equipe.getEsporte().getEvento().getTipoEvento())) {
            throw new RuntimeException("O curso inserido não é permitido nesse esporte!");
        }

        equipe.setNome(form.getNome());

        if (form.getAtletasMatricula()!=null)  {
            participacaoEquipeRepository.deleteByEquipe(equipe);
            List<ParticipacaoEquipe> participacoes = addUsuariosNaEquipe(equipe, form.getAtletasMatricula());
        }

        equipeRepository.save(equipe);

        return EquipeDTO.deModel(equipe);
    }

    @Transactional
    public EquipeDTO deleteEquipe(String nomeEquipe) {
        Equipe equipe = equipeRepository.findByNome(nomeEquipe);
        if (equipe==null) {
            throw new RuntimeException("Equipe não encontrado.");
        }

        equipeRepository.delete(equipe);

        return EquipeDTO.deModel(equipe);
    }

}