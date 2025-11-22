package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VagaRequestDto(
        @NotBlank(message = "A descrição da vaga é obrigatória.")
        @Size(max = 255)
        String descricao,

        @NotNull(message = "O ID da empresa é obrigatório.")
        Long idEmpresa,

        @NotNull(message = "O ID do recrutador é obrigatório.")
        Long idRecrutador
) {}