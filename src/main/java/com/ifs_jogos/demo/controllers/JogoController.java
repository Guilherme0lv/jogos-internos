package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.models.FaseEnum;
import com.ifs_jogos.demo.models.JogoStatusEnum;
import com.ifs_jogos.demo.services.jogo.dto.JogoDTO;
import com.ifs_jogos.demo.services.jogo.form.PlacarForm;
import com.ifs_jogos.demo.services.jogo.JogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jogos")
public class JogoController {

    private final JogoService jogoService;

    @PostMapping("/gerarJogos/{grupoId}")
    public ResponseEntity<List<JogoDTO>> gerarJogos(@PathVariable("grupoId") Integer grupoId) {
        List<JogoDTO> jogos = jogoService.gerarJogos(grupoId);
        return ResponseEntity.status(201).body(jogos);
    }

    @GetMapping
    public ResponseEntity<List<JogoDTO>> getJogos() {
        List<JogoDTO> jogos = jogoService.getJogos();
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/finalizados/{statusJogo}/{esporteId}")
    public ResponseEntity<List<JogoDTO>> findByStatus(@PathVariable("statusJogo") JogoStatusEnum status,
                                                      @PathVariable("esporteId") Integer esporteId) {
        List<JogoDTO> jogos = jogoService.findByStatus(status, esporteId);
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<JogoDTO>> findByGrupo(@PathVariable("grupoId") Integer grupoId) {
        List<JogoDTO> jogos = jogoService.findByGrupo(grupoId);
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/fase/esporte/{esporteId}")
    public ResponseEntity<List<JogoDTO>> findByFase(@PathVariable("esporteId") Integer esporteId,
                                                              @RequestBody FaseEnum fase) {
        List<JogoDTO> jogos = jogoService.findByFaseAndEsporte(fase, esporteId);
        return ResponseEntity.ok(jogos);
    }

    @PatchMapping("/finalizar")
    public ResponseEntity<JogoDTO> finalizarJogo(@RequestBody PlacarForm form) {
        JogoDTO dto = jogoService.finalizarJogo(form);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/aplicarWO/{id}/equipe/{idEquipe}")
    public ResponseEntity<JogoDTO> aplicarWO(@PathVariable("id") Integer idJogo,
                                             @PathVariable("idEquipe") Integer idEquipe) {
        JogoDTO dto = jogoService.aplicarWO(idJogo, idEquipe);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/desfazerWO/{id}")
    public ResponseEntity<JogoDTO> desfazerWO(@PathVariable("id") Integer idJogo) {
        JogoDTO dto = jogoService.desfazerWO(idJogo);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllJogos() {
        jogoService.deleteAllJogos();
        return ResponseEntity.ok("Todos os jogos foram deletados com sucesso.");
    }

    @DeleteMapping("/deleteAllJogosGrupo/{esporteId}")
    public ResponseEntity<String> deleteAllJogosDeGrupo(@PathVariable("esporteId") Integer esporteId) {
        jogoService.deleteAllJogosDeGrupo(esporteId);
        return ResponseEntity.ok("Todos os jogos do grupo foram deletados com sucesso.");
    }

    @DeleteMapping("/delete/{jogoId}")
    public ResponseEntity<String> deleteJogoByID(@PathVariable("jogoId") Integer jogoId) {
        jogoService.deleteJogoById(jogoId);
        return ResponseEntity.ok("Jogo deletado com sucesso.");
    }
}
