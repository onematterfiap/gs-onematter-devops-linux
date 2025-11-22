package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TesteSubmissaoDto(
        @NotNull(message = "O ID da Candidatura é obrigatório.")
        Long idCandidatura,

        @NotNull(message = "A pontuação é obrigatória.")
        @Min(0) @Max(100)
        Double score
) {}