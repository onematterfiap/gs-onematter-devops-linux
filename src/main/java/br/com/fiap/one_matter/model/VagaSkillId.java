package br.com.fiap.one_matter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class VagaSkillId implements Serializable {

    @Column(name = "id_vaga")
    private Long vagaId;

    @Column(name = "id_skill")
    private Long skillId;
}