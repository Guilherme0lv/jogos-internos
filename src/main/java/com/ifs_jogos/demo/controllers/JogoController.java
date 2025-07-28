package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.models.FaseEnum;
import com.ifs_jogos.demo.models.JogoStatusEnum;
import com.ifs_jogos.demo.services.jogo.dto.JogoDTO;
import com.ifs_jogos.demo.services.jogo.form.PlacarForm;
import com.ifs_jogos.demo.services.jogo.JogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jogos")
public class JogoController {

    private final JogoService jogoService;

    @PostMapping("/gerarJogos/{grupoId}")
    public List<JogoDTO> gerarJogos(@PathVariable("grupoId") Integer grupoId) {
        return jogoService.gerarJogos(grupoId);
    }

    @GetMapping
    public List<JogoDTO> getJogos() {
        return jogoService.getJogos();
    }

    @GetMapping("/finalizados/{statusJogo}/{esporteId}")
    public List<JogoDTO> findByStatus(@PathVariable("statusJogo")JogoStatusEnum status, @PathVariable("esporteId") Integer esporteId) {
        return jogoService.findByStatus(status, esporteId);
    }

    @GetMapping("/grupo/{grupoId}")
    public List<JogoDTO> findByGrupo(@PathVariable("grupoId") Integer grupoId) {
        return jogoService.findByGrupo(grupoId);
    }

    @GetMapping("/fase/esporte/{esporteId}")
    public List<JogoDTO> findByFaseAndEsporte(@PathVariable("esporteId") Integer esporteId, @RequestBody FaseEnum fase) {
        return jogoService.findByFaseAndEsporte(fase, esporteId);
    }

    @PatchMapping("/finalizar")
    public JogoDTO finalizarJogo(@RequestBody PlacarForm form) {
        return jogoService.finalizarJogo(form);
    }

    @PatchMapping("/aplicarWO/{id}/equipe/{idEquipe}")
    public JogoDTO aplicarWO(@PathVariable("id") Integer idJogo, @PathVariable("idEquipe") Integer idEquipe) {
        return jogoService.aplicarWO(idJogo, idEquipe);
    }

    @PatchMapping("/desfazerWO/{id}")
    public JogoDTO desfazerWO(@PathVariable("id") Integer idJogo) {
        return jogoService.desfazerWO(idJogo);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllJogos() {
        jogoService.deleteAllJogos();
    }

    @DeleteMapping("/deleteAllJogosGrupo/{esporteId}")
    public void deleteAllJogosDeGrupo(@PathVariable("esporteId") Integer esporteId) {
        jogoService.deleteAllJogosDeGrupo(esporteId);
    }

    @DeleteMapping("/delete/{jogoId}")
    public JogoDTO deleteJogoByID(@PathVariable("jogoId") Integer jogoId) {
        return jogoService.deleteJogoById(jogoId);
    }
}
