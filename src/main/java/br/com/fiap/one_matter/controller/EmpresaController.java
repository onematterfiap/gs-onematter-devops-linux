package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.EmpresaRequestDto;
import br.com.fiap.one_matter.dto.response.EmpresaResponseDto;
import br.com.fiap.one_matter.model.Empresa;
import br.com.fiap.one_matter.service.EmpresaService;
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
@RequestMapping("/empresas")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Protege todas as rotas da classe
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<Page<EmpresaResponseDto>> listarEmpresas(
            @RequestParam(required = false) Optional<Integer> deleted,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Empresa> page = empresaService.listar(deleted, pageable);
        return ResponseEntity.ok(page.map(EmpresaResponseDto::fromEmpresa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDto> buscarEmpresaPorId(@PathVariable Long id) {
        Empresa empresa = empresaService.buscarPorIdAtiva(id);
        return ResponseEntity.ok(EmpresaResponseDto.fromEmpresa(empresa));
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDto> criarEmpresa(@RequestBody @Valid EmpresaRequestDto dto) {
        Empresa empresa = empresaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(EmpresaResponseDto.fromEmpresa(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDto> atualizarEmpresa(
            @PathVariable Long id,
            @RequestBody @Valid EmpresaRequestDto dto) {
        Empresa empresa = empresaService.atualizar(id, dto);
        return ResponseEntity.ok(EmpresaResponseDto.fromEmpresa(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}