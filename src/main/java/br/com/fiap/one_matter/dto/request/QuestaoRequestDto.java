package br.com.fiap.one_matter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestaoRequestDto(
        Integer nivelDificuldade,

        @NotBlank @Size(max = 255)
        String enunciado,

        @Size(max = 255) String alternativa1,
        @Size(max = 255) String alternativa2,
        @Size(max = 255) String alternativa3,
        @Size(max = 255) String alternativa4,
        @Size(max = 255) String alternativa5
) {}