package br.com.fiap.one_matter.dto.response;

import lombok.Builder;

@Builder
public record TokenDto(
        String token,
        String refreshToken,
        String tipo
) {}