package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SkillRequestDto(
        @NotBlank @Size(max = 20)
        String nome,

        @Size(max = 20)
        String categoria
) {}