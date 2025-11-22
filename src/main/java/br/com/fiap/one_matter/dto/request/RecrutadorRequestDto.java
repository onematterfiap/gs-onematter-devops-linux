package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecrutadorRequestDto(
        @NotBlank @Size(max = 90)
        String nome,

        @Size(max = 11)
        String telefone,

        @NotBlank @Size(min = 11, max = 11)
        String cpf,

        @NotBlank @Email @Size(max = 30)
        String email,

        @NotBlank @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha,

        @NotNull
        Long idEmpresa
) {}