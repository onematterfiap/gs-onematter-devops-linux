package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Candidatura;

import java.time.Instant;
import java.util.List;

public record CandidaturaDetalheDto(
        Long id,
        Long idVaga,
        String descricaoVaga,
        String nomeEmpresa,
        Instant dataCandidatura,
        List<StatusCandidaturaDetalheDto> statusHistorico
) {
    public static CandidaturaDetalheDto fromCandidatura(Candidatura c) {
        return new CandidaturaDetalheDto(
                c.getId(),
                c.getVaga().getId(),
                c.getVaga().getDescricao(),
                c.getVaga().getEmpresa().getNome(),
                c.getDataCriacao(),
                c.getStatusHistorico().stream()
                        .map(StatusCandidaturaDetalheDto::fromStatus)
                        .toList()
        );
    }
}