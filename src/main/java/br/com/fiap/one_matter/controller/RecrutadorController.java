package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.RecrutadorRequestDto;
import br.com.fiap.one_matter.dto.response.RecrutadorResponseDto;
import br.com.fiap.one_matter.model.Recrutador;
import br.com.fiap.one_matter.service.RecrutadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/recrutadores")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RecrutadorController {

    private final RecrutadorService recrutadorService;

    @GetMapping
    public ResponseEntity<Page<RecrutadorResponseDto>> listarRecrutadores(
            @RequestParam(required = false) Optional<Integer> deleted,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Recrutador> page = recrutadorService.listar(deleted, pageable);
        return ResponseEntity.ok(page.map(RecrutadorResponseDto::fromRecrutador));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecrutadorResponseDto> buscarRecrutadorPorId(@PathVariable Long id) {
        Recrutador r = recrutadorService.buscarPorIdAtivo(id);
        return ResponseEntity.ok(RecrutadorResponseDto.fromRecrutador(r));
    }

    @PostMapping
    public ResponseEntity<RecrutadorResponseDto> criarRecrutador(@RequestBody @Valid RecrutadorRequestDto dto) {
        Recrutador r = recrutadorService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(RecrutadorResponseDto.fromRecrutador(r));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecrutadorResponseDto> atualizarRecrutador(
            @PathVariable Long id,
            @RequestBody @Valid RecrutadorRequestDto dto) {
        Recrutador r = recrutadorService.atualizar(id, dto);
        return ResponseEntity.ok(RecrutadorResponseDto.fromRecrutador(r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRecrutador(@PathVariable Long id) {
        recrutadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}