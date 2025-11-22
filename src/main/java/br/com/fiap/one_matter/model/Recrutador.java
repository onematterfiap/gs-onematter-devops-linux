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
@Table(name = "TB_ONEM_RECRUTADOR")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recrutador {

    @Id
    @SequenceGenerator(name = "seq_recrutador", sequenceName = "SQ_ONEM_RECRUTADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recrutador")
    @Column(name = "id_recrutador")
    private Long id;

    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false, nullable = false)
    private Instant dataCriacao;

    @NotNull
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Integer deleted = 0;

    @NotNull
    @Column(name = "nm_recrutador", length = 90)
    private String nome;

    @Column(name = "nr_telefone", length = 11)
    private String telefone;

    @NotNull
    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @NotNull
    @Column(name = "email", length = 30, unique = true)
    private String email;

    @NotNull
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "recrutador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vaga> vagas;
}