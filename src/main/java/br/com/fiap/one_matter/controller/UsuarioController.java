package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.AtualizarUsuarioDto;
import br.com.fiap.one_matter.dto.response.UsuarioPerfilCompletoDto;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.service.UsuarioGerenciamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioGerenciamentoService usuarioGerenciamentoService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioPerfilCompletoDto> buscarUsuarioLogado(@AuthenticationPrincipal Usuario usuario) {
        Usuario perfil = usuarioGerenciamentoService.buscarPerfilCompletoPorId(usuario.getId());
        return ResponseEntity.ok(UsuarioPerfilCompletoDto.fromUsuario(perfil));
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioPerfilCompletoDto> atualizarUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @RequestBody @Valid AtualizarUsuarioDto dto) {

        Usuario usuarioAtualizado = usuarioGerenciamentoService.atualizar(usuario.getEmail(), dto);

        Usuario perfil = usuarioGerenciamentoService.buscarPerfilCompletoPorId(usuarioAtualizado.getId());
        return ResponseEntity.ok(UsuarioPerfilCompletoDto.fromUsuario(perfil));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deletarUsuario(@AuthenticationPrincipal Usuario usuario) {
        usuarioGerenciamentoService.deletar(usuario.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}