package br.com.fiap.one_matter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "TB_ONEM_CANDIDATURA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidatura {

    @Id
    @SequenceGenerator(name = "seq_candidatura", sequenceName = "SQ_ONEM_CANDIDATURA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_candidatura")
    @Column(name = "id_candidatura")
    private Long id;

    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false, nullable = false)
    private Instant dataCriacao;

    @NotNull
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Integer deleted = 0;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_candidato", nullable = false)
    private Usuario candidato;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;

    @OneToMany(mappedBy = "candidatura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StatusCandidatura> statusHistorico;

    @Column(name = "nr_nota_final")
    private Double notaFinal;
}