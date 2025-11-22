package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.dto.request.CadastroUsuarioGerenciadoDto;
import br.com.fiap.one_matter.dto.response.UsuarioPerfilCompletoDto;
import br.com.fiap.one_matter.dto.response.UsuarioListagemDto;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.service.UsuarioGerenciamentoService;
import br.com.fiap.one_matter.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class GerenciamentoController {

    private final UsuarioService usuarioService;
    private final UsuarioGerenciamentoService usuarioGerenciamentoService;
    private final PagedResourcesAssembler<UsuarioListagemDto> pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<UsuarioPerfilCompletoDto> criarUsuarioGerenciado(@RequestBody @Valid CadastroUsuarioGerenciadoDto dados) {

        Usuario usuarioSalvo = usuarioService.criarGerenciado(dados);

        Usuario perfil = usuarioGerenciamentoService.buscarPerfilCompletoPorId(usuarioSalvo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioPerfilCompletoDto.fromUsuario(perfil));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPerfilCompletoDto> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioGerenciamentoService.buscarPerfilCompletoPorId(id);
        return ResponseEntity.ok(UsuarioPerfilCompletoDto.fromUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UsuarioListagemDto>>> listarUsuarios(
            @RequestParam(required = false) Optional<Integer> deleted,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Usuario> usuariosPage = usuarioGerenciamentoService.listar(deleted, pageable);

        Page<UsuarioListagemDto> dtoPage = usuariosPage.map(usuario -> new UsuarioListagemDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getDataCriacao(),
                usuario.getDeleted(),
                usuario.getCpf(),
                usuario.getDataNascimento(),
                usuario.getGenero(),
                usuario.getTelefone()
        ));

        PagedModel<EntityModel<UsuarioListagemDto>> pagedModel = pagedResourcesAssembler.toModel(dtoPage);

        return ResponseEntity.ok(pagedModel);
    }
}