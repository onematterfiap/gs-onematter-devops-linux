package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Empresa;
import java.time.Instant;

public record EmpresaResponseDto(
        Long id,
        String nome,
        String telefone,
        String endereco,
        String cnpj,
        String email,
        Instant dataCriacao,
        Integer deleted
) {
    public static EmpresaResponseDto fromEmpresa(Empresa e) {
        return new EmpresaResponseDto(
                e.getId(),
                e.getNome(),
                e.getTelefone(),
                e.getEndereco(),
                e.getCnpj(),
                e.getEmail(),
                e.getDataCriacao(),
                e.getDeleted()
        );
    }
}