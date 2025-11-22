package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Formacao;

import java.time.Instant;

public record FormacaoResponseDto(
        Long id,
        String nomeFormacao,
        String descricao,
        String instituicao,
        Instant dataInicio,
        Instant dataFim,
        Long idCandidato
) {
    public static FormacaoResponseDto fromFormacao(Formacao f) {
        return new FormacaoResponseDto(
                f.getId(),
                f.getNomeFormacao(),
                f.getDescricao(),
                f.getInstituicao(),
                f.getDataInicio(),
                f.getDataFim(),
                f.getCandidato().getId()
        );
    }
}