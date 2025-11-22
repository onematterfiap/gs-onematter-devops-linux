package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.AssociacaoRequestDto;
import br.com.fiap.one_matter.dto.request.TesteRequestDto;
import br.com.fiap.one_matter.dto.request.TesteSubmissaoDto;
import br.com.fiap.one_matter.dto.response.QuestaoResponseDto;
import br.com.fiap.one_matter.dto.response.TesteResponseDto;
import br.com.fiap.one_matter.model.Questao;
import br.com.fiap.one_matter.model.Teste;
import br.com.fiap.one_matter.service.TesteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/testes")
@RequiredArgsConstructor
public class TesteController {

    private final TesteService testeService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TesteResponseDto> buscarTestePorId(@PathVariable Long id) {
        Teste t = testeService.buscarPorId(id);
        return ResponseEntity.ok(TesteResponseDto.fromTeste(t));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TesteResponseDto> criarTeste(@RequestBody @Valid TesteRequestDto dto) {
        Teste t = testeService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(TesteResponseDto.fromTeste(t));
    }

    @PostMapping("/associar-questao")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> associarQuestao(@RequestBody @Valid AssociacaoRequestDto dto) {
        testeService.associarQuestao(dto.idPrincipal(), dto.idAssociado());
        return ResponseEntity.ok("Questão associada.");
    }

    // --- FLUXO ESP32 ---

    @PostMapping("/submit-score")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> submitScore(@RequestBody @Valid TesteSubmissaoDto dto) {
        try {
            // Chama service que chama a PROCEDURE
            String situacao = testeService.finalizarTeste(dto.idCandidatura(), dto.score());
            return ResponseEntity.ok("Prova finalizada. Situação: " + situacao);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/candidatura/{idCandidatura}/questoes")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<QuestaoResponseDto>> listarQuestoes(@PathVariable Long idCandidatura) {
        try {
            // Ao listar, a procedure SP_REGISTRAR_INICIO é chamada internamente
            List<Questao> questoes = testeService.buscarQuestoesDoTestePorCandidatura(idCandidatura);
            return ResponseEntity.ok(questoes.stream().map(QuestaoResponseDto::fromQuestao).toList());
        } catch (IllegalStateException e) {
            // Se a Function do banco disser que já concluiu
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}