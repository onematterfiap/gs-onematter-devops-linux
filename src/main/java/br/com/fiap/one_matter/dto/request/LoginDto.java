package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "O e-mail não pode ser vazio.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "A senha não pode ser vazia.")
        String senha
) {}