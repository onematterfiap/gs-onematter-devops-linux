package br.com.fiap.one_matter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class UsuarioSkillId implements Serializable {

    @Column(name = "id_candidato")
    private Long candidatoId;

    @Column(name = "id_skill")
    private Long skillId;
}