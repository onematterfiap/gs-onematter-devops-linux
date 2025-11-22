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
@Table(name = "TB_ONEM_SKILL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @SequenceGenerator(name = "seq_skill", sequenceName = "SQ_ONEM_SKILL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_skill")
    @Column(name = "id_skill")
    private Long id;

    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false, nullable = false)
    private Instant dataCriacao;

    @NotNull
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Integer deleted = 0;

    @NotNull
    @Column(name = "nm_skill", length = 20)
    private String nome;

    @Column(name = "categoria", length = 20)
    private String categoria;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VagaSkill> vagaSkills;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UsuarioSkill> usuarioSkills;
}