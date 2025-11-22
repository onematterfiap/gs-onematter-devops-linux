package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.StatusCandidatura;
import java.time.Instant;

public record StatusCandidaturaDetalheDto(
        String statusDescricao,
        Instant dataAtualizacao
) {
    public static StatusCandidaturaDetalheDto fromStatus(StatusCandidatura s) {
        return new StatusCandidaturaDetalheDto(
                s.getStatusDescricao(),
                s.getDataAtualizacao()
        );
    }
}