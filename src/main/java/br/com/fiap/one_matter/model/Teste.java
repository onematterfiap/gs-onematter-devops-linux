package br.com.fiap.one_matter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "TB_ONEM_TESTE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teste {

    @Id
    @SequenceGenerator(name = "seq_teste", sequenceName = "SQ_ONEM_TESTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_teste")
    @Column(name = "id_teste")
    private Long id;

    @NotNull
    @Column(name = "dt_inicio")
    private Instant dataInicio;

    @Column(name = "dt_fim")
    private Instant dataFim;

    @Column(name = "pontuacao_teste")
    private Double pontuacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;

    @OneToMany(mappedBy = "teste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TesteQuestao> testeQuestoes;
}