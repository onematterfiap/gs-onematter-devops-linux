package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.QuestaoRequestDto;
import br.com.fiap.one_matter.dto.response.QuestaoResponseDto;
import br.com.fiap.one_matter.model.Questao;
import br.com.fiap.one_matter.service.QuestaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questoes") // Banco de questões
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class QuestaoController {

    private final QuestaoService questaoService;

    @GetMapping
    public ResponseEntity<Page<QuestaoResponseDto>> listarQuestoes(
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Questao> page = questaoService.listar(pageable);
        return ResponseEntity.ok(page.map(QuestaoResponseDto::fromQuestao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestaoResponseDto> buscarQuestaoPorId(@PathVariable Long id) {
        Questao q = questaoService.buscarPorId(id);
        return ResponseEntity.ok(QuestaoResponseDto.fromQuestao(q));
    }

    @PostMapping
    public ResponseEntity<QuestaoResponseDto> criarQuestao(@RequestBody @Valid QuestaoRequestDto dto) {
        Questao q = questaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(QuestaoResponseDto.fromQuestao(q));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestaoResponseDto> atualizarQuestao(
            @PathVariable Long id,
            @RequestBody @Valid QuestaoRequestDto dto) {
        Questao q = questaoService.atualizar(id, dto);
        return ResponseEntity.ok(QuestaoResponseDto.fromQuestao(q));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuestao(@PathVariable Long id) {
        questaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}