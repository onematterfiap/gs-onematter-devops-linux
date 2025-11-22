package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record TesteRequestDto(
        @NotNull
        Instant dataInicio,

        Instant dataFim,

        @NotNull
        Long idVaga
) {}