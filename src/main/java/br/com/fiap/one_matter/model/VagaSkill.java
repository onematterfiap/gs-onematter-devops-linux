package br.com.fiap.one_matter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 💡 CORREÇÃO: A classe VagaSkillId foi removida deste arquivo
// e agora é referenciada publicamente.

@Entity
@Table(name = "TB_ONEM_VAGA_SKILL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VagaSkill {

    @EmbeddedId
    private VagaSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vagaId")
    @JoinColumn(name = "id_vaga")
    private Vaga vaga;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "id_skill")
    private Skill skill;
}