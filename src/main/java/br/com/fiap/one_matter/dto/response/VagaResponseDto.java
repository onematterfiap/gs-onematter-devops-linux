package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Vaga;

import java.time.Instant;

public record VagaResponseDto(
        Long id,
        String descricao,
        Long idEmpresa,
        String nomeEmpresa,
        Long idRecrutador,
        String nomeRecrutador,
        Instant dataCriacao,
        Integer deleted
) {
    public static VagaResponseDto fromVaga(Vaga vaga) {
        return new VagaResponseDto(
                vaga.getId(),
                vaga.getDescricao(),
                vaga.getEmpresa().getId(),
                vaga.getEmpresa().getNome(),
                vaga.getRecrutador().getId(),
                vaga.getRecrutador().getNome(),
                vaga.getDataCriacao(),
                vaga.getDeleted()
        );
    }
}