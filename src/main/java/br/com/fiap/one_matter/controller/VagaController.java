package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.VagaRequestDto;
import br.com.fiap.one_matter.dto.response.CandidaturaResponseDto;
import br.com.fiap.one_matter.dto.response.VagaResponseDto;
import br.com.fiap.one_matter.model.Candidatura;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.model.Vaga;
import br.com.fiap.one_matter.service.CandidaturaService;
import br.com.fiap.one_matter.service.VagaService;
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

import java.util.Optional;

@RestController
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagaController {

    private final VagaService vagaService;
    private final CandidaturaService candidaturaService;

    // ABERTO (Público)
    @GetMapping
    public ResponseEntity<Page<VagaResponseDto>> listarVagas(
            @RequestParam(required = false) Optional<Integer> deleted,
            @PageableDefault(size = 10) Pageable pageable) {

        // Força listar apenas ativos se for público
        Integer status = deleted.orElse(0);

        Page<Vaga> vagasPage = vagaService.listar(Optional.of(status), pageable);
        Page<VagaResponseDto> dtoPage = vagasPage.map(VagaResponseDto::fromVaga);
        return ResponseEntity.ok(dtoPage);
    }

    // ABERTO (Público)
    @GetMapping("/{id}")
    public ResponseEntity<VagaResponseDto> buscarVagaPorId(@PathVariable Long id) {
        Vaga vaga = vagaService.buscarPorId(id);
        return ResponseEntity.ok(VagaResponseDto.fromVaga(vaga));
    }

    // FECHADO (Admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> criarVaga(@RequestBody @Valid VagaRequestDto dto) {
        Vaga vaga = vagaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(VagaResponseDto.fromVaga(vaga));
    }

    // FECHADO (Admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> atualizarVaga(
            @PathVariable Long id,
            @RequestBody @Valid VagaRequestDto dto) {
        Vaga vaga = vagaService.atualizar(id, dto);
        return ResponseEntity.ok(VagaResponseDto.fromVaga(vaga));
    }

    // FECHADO (Admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarVaga(@PathVariable Long id) {
        vagaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints de Candidatura ---

    // FECHADO (Candidato)
    @PostMapping("/{idVaga}/candidatar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CandidaturaResponseDto> candidatarAVaga(
            @PathVariable Long idVaga,
            @AuthenticationPrincipal Usuario candidatoLogado) {

        Candidatura candidatura = candidaturaService.candidatar(idVaga, candidatoLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(CandidaturaResponseDto.fromCandidatura(candidatura));
    }

    // FECHADO (Admin - Recrutador)
    @GetMapping("/{idVaga}/candidatos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CandidaturaResponseDto>> listarCandidatosDaVaga(
            @PathVariable Long idVaga,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Candidatura> candidaturas = candidaturaService.listarCandidatosDaVaga(idVaga, pageable);
        return ResponseEntity.ok(candidaturas.map(CandidaturaResponseDto::fromCandidatura));
    }
}