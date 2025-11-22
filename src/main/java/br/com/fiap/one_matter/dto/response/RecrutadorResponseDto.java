package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Recrutador;
import java.time.Instant;

public record RecrutadorResponseDto(
        Long id,
        String nome,
        String telefone,
        String cpf,
        String email,
        Long idEmpresa,
        String nomeEmpresa,
        Instant dataCriacao,
        Integer deleted
) {
    public static RecrutadorResponseDto fromRecrutador(Recrutador r) {
        return new RecrutadorResponseDto(
                r.getId(),
                r.getNome(),
                r.getTelefone(),
                r.getCpf(),
                r.getEmail(),
                r.getEmpresa().getId(),
                r.getEmpresa().getNome(),
                r.getDataCriacao(),
                r.getDeleted()
        );
    }
}