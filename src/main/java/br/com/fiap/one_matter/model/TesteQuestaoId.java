package br.com.fiap.one_matter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class TesteQuestaoId implements Serializable {

    @Column(name = "id_teste")
    private Long testeId;

    @Column(name = "id_questao")
    private Long questaoId;
}