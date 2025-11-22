package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.enums.Genero;
import br.com.fiap.one_matter.enums.UsuarioRole;
import br.com.fiap.one_matter.model.Usuario;

import java.time.Instant;
import java.util.List;

public record UsuarioPerfilCompletoDto(
        Long id,
        String nome,
        String email,
        UsuarioRole role,
        String cpf,
        Instant dataNascimento,
        Genero genero,
        String telefone,
        Instant dataCriacao,

        List<SkillDetalheDto> skills,
        List<FormacaoDetalheDto> formacoes,
        List<CandidaturaDetalheDto> candidaturas
) {
    public static UsuarioPerfilCompletoDto fromUsuario(Usuario u) {
        return new UsuarioPerfilCompletoDto(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getRole(),
                u.getCpf(),
                u.getDataNascimento(),
                u.getGenero(),
                u.getTelefone(),
                u.getDataCriacao(),

                u.getUsuarioSkills().stream()
                        .map(SkillDetalheDto::fromUsuarioSkill)
                        .toList(),
                u.getFormacoes().stream()
                        .map(FormacaoDetalheDto::fromFormacao)
                        .toList(),
                u.getCandidaturas().stream()
                        .filter(c -> c.getDeleted() == 0)
                        .map(CandidaturaDetalheDto::fromCandidatura)
                        .toList()
        );
    }
}