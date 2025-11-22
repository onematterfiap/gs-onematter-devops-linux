package br.com.fiap.one_matter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TB_ONEM_QUESTAO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questao {

    @Id
    @SequenceGenerator(name = "seq_questao", sequenceName = "SQ_ONEM_QUESTAO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_questao")
    @Column(name = "id_questao")
    private Long id;

    @Column(name = "nv_dificuldade")
    private Integer nivelDificuldade;

    @NotNull
    @Column(name = "enunciado_questao", length = 255)
    private String enunciado;

    @Column(name = "alternativa_1", length = 255)
    private String alternativa1;
    @Column(name = "alternativa_2", length = 255)
    private String alternativa2;
    @Column(name = "alternativa_3", length = 255)
    private String alternativa3;
    @Column(name = "alternativa_4", length = 255)
    private String alternativa4;
    @Column(name = "alternativa_5", length = 255)
    private String alternativa5;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TesteQuestao> testeQuestoes;
}