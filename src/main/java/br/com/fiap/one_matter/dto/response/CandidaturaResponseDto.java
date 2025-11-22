package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Candidatura;
import br.com.fiap.one_matter.model.StatusCandidatura;

import java.time.Instant;
import java.util.Comparator;

public record CandidaturaResponseDto(
        Long idCandidatura,
        Instant dataCandidatura,
        Long idCandidato,
        String nomeCandidato,
        Long idVaga,
        String descVaga,
        String nomeEmpresa,
        String ultimoStatus,
        Instant dataUltimaAtualizacao,
        Double notaFinal
) {
    public static CandidaturaResponseDto fromCandidatura(Candidatura c) {
        // CORREÇÃO CRÍTICA: Ordena por ID do Status (o ID é sempre sequencial e garante o último inserido)
        StatusCandidatura statusMaisRecente = c.getStatusHistorico().stream()
                .max(Comparator.comparing(StatusCandidatura::getId))
                .orElse(null);

        String statusTexto = (statusMaisRecente != null) ? statusMaisRecente.getStatusDescricao() : "AGUARDANDO";
        Instant dataStatus = (statusMaisRecente != null) ? statusMaisRecente.getDataAtualizacao() : c.getDataCriacao();

        return new CandidaturaResponseDto(
                c.getId(),
                c.getDataCriacao(),
                c.getCandidato().getId(),
                c.getCandidato().getNome(),
                c.getVaga().getId(),
                c.getVaga().getDescricao(),
                c.getVaga().getEmpresa().getNome(),
                statusTexto,
                dataStatus,
                c.getNotaFinal()
        );
    }
}