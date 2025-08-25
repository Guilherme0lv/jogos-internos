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
@RequestMapping("/jogo")
public class JogoController {

    private final JogoService jogoService;

    @PostMapping("/gerarJogos/{grupoNome}")
    public ResponseEntity<List<JogoDTO>> gerarJogos(@PathVariable("grupoNome") String grupoNome) {
        List<JogoDTO> jogos = jogoService.gerarJogos(grupoNome);
        return ResponseEntity.status(201).body(jogos);
    }

    @GetMapping
    public ResponseEntity<List<JogoDTO>> getJogos() {
        List<JogoDTO> jogos = jogoService.getJogos();
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/finalizados/{statusJogo}/{esporteNome}")
    public ResponseEntity<List<JogoDTO>> findByStatus(@PathVariable("statusJogo") JogoStatusEnum status,
                                                      @PathVariable("esporteNome") String esporteNome) {
        List<JogoDTO> jogos = jogoService.findByStatus(status, esporteNome);
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/grupo/{grupoNome}")
    public ResponseEntity<List<JogoDTO>> findByGrupo(@PathVariable("grupoNome") String grupoNome) {
        List<JogoDTO> jogos = jogoService.findByGrupo(grupoNome);
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/fase/esporte/{esporteNome}")
    public ResponseEntity<List<JogoDTO>> findByFase(@PathVariable("esporteNome") String esporteNome,
                                                    @RequestParam("fase") String fase) {
        List<JogoDTO> jogos = jogoService.findByFaseAndEsporte(fase, esporteNome);
        return ResponseEntity.ok(jogos);
    }

    @PutMapping("/finalizar")
    public ResponseEntity<JogoDTO> finalizarJogo(@RequestBody PlacarForm form) {
        JogoDTO dto = jogoService.finalizarJogo(form);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/aplicarWO/{jogoId}/equipeWin/{equipeNome}")
    public ResponseEntity<JogoDTO> aplicarWO(@PathVariable("jogoId") Integer idJogo,
                                             @PathVariable("equipeNome") String equipeNome) {
        JogoDTO dto = jogoService.aplicarWO(idJogo, equipeNome);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/desfazerWO/{id}")
    public ResponseEntity<JogoDTO> desfazerWO(@PathVariable("id") Integer idJogo) {
        JogoDTO dto = jogoService.desfazerWO(idJogo);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllJogos() {
        jogoService.deleteAllJogos();
        return ResponseEntity.ok("Todos os jogos foram deletados com sucesso.");
    }

    @DeleteMapping("/deleteAllJogosGrupo/{esporteNome}")
    public ResponseEntity<String> deleteAllJogosDeGrupo(@PathVariable("esporteNome") String esporteNome) {
        jogoService.deleteAllJogosDeGrupo(esporteNome);
        return ResponseEntity.ok("Todos os jogos do grupo foram deletados com sucesso.");
    }

    @DeleteMapping("/delete/{jogoId}")
    public ResponseEntity<String> deleteJogoByID(@PathVariable("jogoId") Integer jogoId) {
        jogoService.deleteJogoById(jogoId);
        return ResponseEntity.ok("Jogo deletado com sucesso.");
    }
}
