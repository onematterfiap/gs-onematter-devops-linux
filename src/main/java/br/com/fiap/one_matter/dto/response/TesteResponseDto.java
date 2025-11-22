package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Teste;
import java.time.Instant;

public record TesteResponseDto(
        Long id,
        Instant dataInicio,
        Instant dataFim,
        Double pontuacao,
        Long idVaga
) {
    public static TesteResponseDto fromTeste(Teste t) {
        return new TesteResponseDto(
                t.getId(),
                t.getDataInicio(),
                t.getDataFim(),
                t.getPontuacao(),
                t.getVaga().getId()
        );
    }
}