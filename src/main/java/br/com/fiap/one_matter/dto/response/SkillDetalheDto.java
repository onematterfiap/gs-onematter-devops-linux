package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.UsuarioSkill;

public record SkillDetalheDto(
        Long id,
        String nome,
        String categoria
) {
    public static SkillDetalheDto fromUsuarioSkill(UsuarioSkill us) {
        return new SkillDetalheDto(
                us.getSkill().getId(),
                us.getSkill().getNome(),
                us.getSkill().getCategoria()
        );
    }
}