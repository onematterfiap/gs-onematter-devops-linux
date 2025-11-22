package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.NotNull;

// DTO Genérico para associar duas entidades
public record AssociacaoRequestDto(
        @NotNull Long idPrincipal,
        @NotNull Long idAssociado
) {}