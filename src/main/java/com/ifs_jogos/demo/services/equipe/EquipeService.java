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
        Curso curso = cursoRepository.findById(form.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

        Esporte esporte = esporteRepository.findById(form.getEsporteId())
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

        Campus campus = campusRepository.findById(form.getCampusId())
                .orElseThrow(() -> new RuntimeException("Campus não encontrado."));

        Usuario tecnico = usuarioRepository.findById(form.getTecnicoId())
                .orElseThrow( () -> new RuntimeException("Tecnico não encontrado."));

        if (equipeRepository.existsByCursoAndEsporte(curso, esporte)) {
            throw new RuntimeException("Já existe equipe desse curso cadastrada.");
        }

        if (form.getAtletasId().size() < esporte.getMinAtletas() || form.getAtletasId().size() > esporte.getMaxAtletas()) {
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

        List<ParticipacaoEquipe> participacoes = addUsuariosNaEquipe(equipe, form.getAtletasId());

        return EquipeDTO.deModelPost(equipe, participacoes);
    }

    @Transactional
    private List<ParticipacaoEquipe> addUsuariosNaEquipe(Equipe equipe, List<Integer> usuarioIds) {
        List<Usuario> usuarios = usuarioRepository.findAllById(usuarioIds);

        for (Usuario u : usuarios) {
          if (u.getCurso() != equipe.getCurso()) {
              throw new RuntimeException("Usuario não pertence ao curso da equipe.");
          }
        }

        if (usuarios.size() != usuarioIds.size()) {
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


    public List<EquipeDTO> findByEsporte(Integer esporteID) {
        Esporte esporte = esporteRepository.findById(esporteID)
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

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
    public EquipeDTO updateEquipe(Integer idEquipe, EquipeForm form) {

        Equipe equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrado."));

        if (form.getCursoId()!=null) {
            Curso curso = cursoRepository.findById(form.getCursoId())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado."));
            equipe.setCurso(curso);
        }

        if (form.getEsporteId()!=null) {
            Esporte esporte = esporteRepository.findById(form.getEsporteId())
                    .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

            if (form.getAtletasId().size() < esporte.getMinAtletas() || form.getAtletasId().size() > esporte.getMaxAtletas()) {
                throw new RuntimeException("Tamanho inválido da equipe");
            }
            equipe.setEsporte(esporte);
        }
        if (form.getCampusId()!=null) {
            Campus campus = campusRepository.findById(form.getCampusId())
                    .orElseThrow(() -> new RuntimeException("Campus não encontrado."));
            equipe.setCampus(campus);
        }
        if (form.getTecnicoId()!=null) {
            Usuario tecnico = usuarioRepository.findById(form.getTecnicoId())
                    .orElseThrow(() -> new RuntimeException("Usuario não encontrado."));
            equipe.setTecnico(tecnico);
        }

        if (!equipe.getCurso().getTipoCurso().paraString().equals(equipe.getEsporte().getEvento().getTipoEvento())) {
            throw new RuntimeException("O curso inserido não é permitido nesse esporte!");
        }

        equipe.setNome(form.getNome());

        if (form.getAtletasId()!=null)  {
            participacaoEquipeRepository.deleteByEquipe(equipe);
            List<ParticipacaoEquipe> participacoes = addUsuariosNaEquipe(equipe, form.getAtletasId());
        }

        equipeRepository.save(equipe);

        return EquipeDTO.deModel(equipe);
    }

    @Transactional
    public EquipeDTO deleteEquipe(Integer id) {
        Equipe equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrado."));

        equipeRepository.delete(equipe);

        return EquipeDTO.deModel(equipe);
    }

}