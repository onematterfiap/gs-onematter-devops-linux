// one-matter-api/src/main/java/br/com/fiap/one_matter/controller/CandidatoController.java
package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.FormacaoRequestDto;
import br.com.fiap.one_matter.dto.response.CandidaturaDetalheDto; // Importar DTO de Detalhes
import br.com.fiap.one_matter.dto.response.CandidaturaResponseDto;
import br.com.fiap.one_matter.dto.response.FormacaoResponseDto;
import br.com.fiap.one_matter.model.Candidatura;
import br.com.fiap.one_matter.model.Formacao;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.service.CandidaturaService;
import br.com.fiap.one_matter.service.FormacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidato/me")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CandidatoController {

    private final FormacaoService formacaoService;
    private final CandidaturaService candidaturaService;

    // --- Formação (existente) ---

    @PostMapping("/formacao")
    public ResponseEntity<FormacaoResponseDto> adicionarFormacao(
            @RequestBody @Valid FormacaoRequestDto dto,
            @AuthenticationPrincipal Usuario candidatoLogado) {

        Formacao formacao = formacaoService.criar(dto, candidatoLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(FormacaoResponseDto.fromFormacao(formacao));
    }

    @GetMapping("/formacao")
    public ResponseEntity<List<FormacaoResponseDto>> listarMinhasFormacoes(
            @AuthenticationPrincipal Usuario candidatoLogado) {

        List<Formacao> formacoes = formacaoService.listarDoCandidato(candidatoLogado.getId());
        List<FormacaoResponseDto> dtos = formacoes.stream().map(FormacaoResponseDto::fromFormacao).toList();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/formacao/{idFormacao}")
    public ResponseEntity<Void> removerFormacao(
            @PathVariable Long idFormacao,
            @AuthenticationPrincipal Usuario candidatoLogado) {

        formacaoService.deletar(idFormacao, candidatoLogado.getId());
        return ResponseEntity.noContent().build();
    }

    // --- Candidaturas (existente) ---

    @GetMapping("/candidaturas")
    public ResponseEntity<Page<CandidaturaResponseDto>> listarMinhasCandidaturas(
            @AuthenticationPrincipal Usuario candidatoLogado,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Candidatura> candidaturas = candidaturaService.listarMinhasCandidaturas(candidatoLogado, pageable);
        return ResponseEntity.ok(candidaturas.map(CandidaturaResponseDto::fromCandidatura));
    }

    @GetMapping("/candidaturas/{idCandidatura}")
    public ResponseEntity<CandidaturaDetalheDto> buscarDetalhesCandidatura(@PathVariable Long idCandidatura) {
        Candidatura c = candidaturaService.buscarDetalhesPorId(idCandidatura);
        return ResponseEntity.ok(CandidaturaDetalheDto.fromCandidatura(c));
    }

    @DeleteMapping("/candidaturas/{idCandidatura}")
    public ResponseEntity<Void> cancelarCandidatura(
            @PathVariable Long idCandidatura,
            @AuthenticationPrincipal Usuario candidatoLogado) {

        candidaturaService.cancelar(idCandidatura, candidatoLogado);
        return ResponseEntity.noContent().build();
    }
}