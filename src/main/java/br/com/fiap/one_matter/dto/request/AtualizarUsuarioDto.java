package br.com.fiap.one_matter.dto.request;

import br.com.fiap.one_matter.enums.Genero;
import br.com.fiap.one_matter.enums.UsuarioRole;
import jakarta.validation.constraints.Size;

public record AtualizarUsuarioDto(
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String nome,

        UsuarioRole role,

        @Size(max = 10, message = "O gênero deve ter no máximo 10 caracteres.")
        Genero genero,

        @Size(max = 13, message = "O telefone deve ter no máximo 13 caracteres.")
        String telefone
) {}