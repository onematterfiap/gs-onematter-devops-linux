package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record FormacaoRequestDto(
        @NotBlank(message = "Nome da formação é obrigatório.")
        String nomeFormacao,

        String descricao,

        @NotBlank(message = "Nome da instituição é obrigatório.")
        String instituicao,

        @NotNull(message = "Data de início é obrigatória.")
        Instant dataInicio,

        Instant dataFim
) {}