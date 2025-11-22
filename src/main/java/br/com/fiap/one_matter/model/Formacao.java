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
@Table(name = "TB_ONEM_FORMACAO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Formacao {

    @Id
    @SequenceGenerator(name = "seq_formacao", sequenceName = "SQ_ONEM_FORMACAO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_formacao")
    @Column(name = "id_formacao")
    private Long id;

    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false, nullable = false)
    private Instant dataCriacao;

    @NotNull
    @Column(name = "nm_formacao", length = 80)
    private String nomeFormacao;

    @Column(name = "ds_formacao", length = 255)
    private String descricao;

    @NotNull
    @Column(name = "nm_instituicao", length = 80)
    private String instituicao;

    @NotNull
    @Column(name = "dt_inicio")
    private Instant dataInicio;

    @Column(name = "dt_fim")
    private Instant dataFim;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_candidato", nullable = false)
    private Usuario candidato;
}