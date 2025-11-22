package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmpresaRequestDto(
        @NotBlank @Size(max = 90)
        String nome,

        @Size(max = 11)
        String telefone,

        @Size(max = 80)
        String endereco,

        @NotBlank @Size(min = 14, max = 14)
        String cnpj,

        @NotBlank @Email @Size(max = 30)
        String email

        // (O seu diagrama não inclui senha para empresa, apenas para recrutador)
) {}