package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Skill;
import java.time.Instant;

public record SkillResponseDto(
        Long id,
        String nome,
        String categoria,
        Instant dataCriacao,
        Integer deleted
) {
    public static SkillResponseDto fromSkill(Skill s) {
        return new SkillResponseDto(
                s.getId(),
                s.getNome(),
                s.getCategoria(),
                s.getDataCriacao(),
                s.getDeleted()
        );
    }
}