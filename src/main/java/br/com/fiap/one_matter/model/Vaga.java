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
@Table(name = "TB_ONEM_VAGA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vaga {

    @Id
    @SequenceGenerator(name = "seq_vaga", sequenceName = "SQ_ONEM_VAGA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vaga")
    @Column(name = "id_vaga")
    private Long id;

    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false, nullable = false)
    private Instant dataCriacao;

    @NotNull
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Integer deleted = 0;

    @NotNull
    @Column(name = "ds_vaga", length = 255)
    private String descricao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recrutador", nullable = false)
    private Recrutador recrutador;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Candidatura> candidaturas;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Teste> testes;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VagaSkill> vagaSkills;
}