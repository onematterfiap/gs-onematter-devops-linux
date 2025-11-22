package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Formacao;
import java.time.Instant;

public record FormacaoDetalheDto(
        Long id,
        String nomeFormacao,
        String instituicao,
        Instant dataInicio,
        Instant dataFim
) {
    public static FormacaoDetalheDto fromFormacao(Formacao f) {
        return new FormacaoDetalheDto(
                f.getId(),
                f.getNomeFormacao(),
                f.getInstituicao(),
                f.getDataInicio(),
                f.getDataFim()
        );
    }
}