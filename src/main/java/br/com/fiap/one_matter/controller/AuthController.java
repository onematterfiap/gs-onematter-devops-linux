package br.com.fiap.one_matter.controller;

import br.com.fiap.one_matter.config.AuthenticationService;
import br.com.fiap.one_matter.config.TokenService;
import br.com.fiap.one_matter.dto.request.CadastroUsuarioDto;
import br.com.fiap.one_matter.dto.request.LoginDto;
import br.com.fiap.one_matter.dto.request.UsuarioResponseHateoas;
import br.com.fiap.one_matter.dto.response.TokenDto;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto dados) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        Authentication authentication = manager.authenticate(authenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String token = tokenService.gerarAccessToken(usuario);
        String refreshToken = tokenService.gerarRefreshToken(usuario);

        return ResponseEntity.ok(TokenDto.builder()
                .token(token)
                .refreshToken(refreshToken)
                .tipo("Bearer")
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody TokenDto dados) {
        String tokenRefresh = dados.refreshToken();

        if (tokenRefresh == null || tokenRefresh.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String email = tokenService.getUsername(tokenRefresh);

            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Usuario usuario = (Usuario) authenticationService.loadUserByUsername(email);

            String novoAccessToken = tokenService.gerarAccessToken(usuario);
            String novoRefreshToken = tokenService.gerarRefreshToken(usuario);

            return ResponseEntity.ok(TokenDto.builder()
                    .token(novoAccessToken)
                    .refreshToken(novoRefreshToken)
                    .tipo("Bearer")
                    .build());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseHateoas> register(@RequestBody @Valid CadastroUsuarioDto dados) {

        Usuario usuarioSalvo = usuarioService.criarDefaultUser(dados);

        UsuarioResponseHateoas respostaHateoas;
        respostaHateoas = new UsuarioResponseHateoas(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getRole(),
                usuarioSalvo.getDataCriacao(),
                usuarioSalvo.getCpf(),
                usuarioSalvo.getDataNascimento(),
                usuarioSalvo.getGenero(),
                usuarioSalvo.getTelefone()
        );

        // Adiciona os Links HATEOAS (Nível 3)
        respostaHateoas.add(
                linkTo(methodOn(AuthController.class).login(null))
                        .withRel("login")
                        .withType("POST")
                        .withTitle("Fazer login para obter o Access Token e Refresh Token")
        );

        respostaHateoas.add(
                linkTo(methodOn(AuthController.class).refreshToken(null))
                        .withRel("refresh_token")
                        .withType("POST")
                        .withTitle("Renovar o Access Token usando o Refresh Token")
        );

        // Link SELF (para esta mesma operação)
        respostaHateoas.add(
                linkTo(methodOn(AuthController.class).register(null))
                        .withSelfRel()
                        .withType("POST")
        );


        return ResponseEntity.status(HttpStatus.CREATED).body(respostaHateoas);
    }
}