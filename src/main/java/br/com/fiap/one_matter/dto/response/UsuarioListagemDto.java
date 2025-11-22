package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.enums.Genero;
import br.com.fiap.one_matter.enums.UsuarioRole;
import java.time.Instant;

public record UsuarioListagemDto(
        Long id,
        String nome,
        String email,
        UsuarioRole role,
        Instant dataCriacao,
        Integer deleted,
        String cpf,
        Instant dataNascimento,
        Genero genero,
        String telefone
) {}