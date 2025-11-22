package br.com.fiap.one_matter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "TB_ONEM_STATUS_CANDIDATURA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusCandidatura {

    @Id
    @SequenceGenerator(name = "seq_status_cand", sequenceName = "SQ_ONEM_STATUS_CAND", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_status_cand")
    @Column(name = "id_status_candidatura")
    private Long id;

    @CreationTimestamp
    @Column(name = "dt_atualizacao", nullable = false)
    private Instant dataAtualizacao;

    @NotNull
    @Column(name = "ds_status", length = 30)
    private String statusDescricao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_candidatura", nullable = false)
    private Candidatura candidatura;
}