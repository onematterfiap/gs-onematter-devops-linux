package br.com.fiap.one_matter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_ONEM_CANDIDATO_SKILL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSkill {

    @EmbeddedId
    private UsuarioSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("candidatoId")
    @JoinColumn(name = "id_candidato")
    private Usuario candidato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "id_skill")
    private Skill skill;
}