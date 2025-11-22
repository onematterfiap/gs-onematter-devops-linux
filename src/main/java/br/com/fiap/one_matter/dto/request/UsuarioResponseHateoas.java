package br.com.fiap.one_matter.dto.request;

import br.com.fiap.one_matter.enums.Genero;
import br.com.fiap.one_matter.enums.UsuarioRole;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import java.time.Instant;

@Getter
public class UsuarioResponseHateoas extends RepresentationModel<UsuarioResponseHateoas> {

    private final Long id;
    private final String nome;
    private final String email;
    private final UsuarioRole role;
    private final Instant dataCriacao;
    private final String cpf;
    private final Instant dataNascimento;
    private final Genero genero;
    private final String telefone;

    public UsuarioResponseHateoas(Long id, String nome, String email, UsuarioRole role, Instant dataCriacao, String cpf, Instant dataNascimento, Genero genero, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
        this.dataCriacao = dataCriacao;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
    }
}