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
@Table(name = "TB_ONEM_EMPRESA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @SequenceGenerator(name = "seq_empresa", sequenceName = "SQ_ONEM_EMPRESA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    @Column(name = "id_empresa")
    private Long id;

    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false, nullable = false)
    private Instant dataCriacao;

    @NotNull
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Integer deleted = 0;

    @NotNull
    @Column(name = "nm_empresa", length = 90)
    private String nome;

    @Column(name = "nr_telefone", length = 11)
    private String telefone;

    @Column(name = "endereco", length = 80)
    private String endereco;

    @NotNull
    @Column(name = "cnpj", length = 14, unique = true)
    private String cnpj;

    @NotNull
    @Column(name = "email", length = 30, unique = true)
    private String email;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recrutador> recrutadores;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vaga> vagas;
}