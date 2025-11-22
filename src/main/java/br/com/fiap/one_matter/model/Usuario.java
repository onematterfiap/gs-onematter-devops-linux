package br.com.fiap.one_matter.model;

import br.com.fiap.one_matter.enums.UsuarioRole;
import br.com.fiap.one_matter.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TB_ONEM_CANDIDATO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @SequenceGenerator(name = "seq_candidato", sequenceName = "SQ_ONEM_CANDIDATO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_candidato")
    @Column(name = "id_candidato")
    private Long id;

    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false, nullable = false)
    private Instant dataCriacao;

    @NotNull
    @Column(name = "nm_candidato", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "ds_email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "ds_senha_hash", nullable = false)
    private String senhaHash;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Integer deleted = 0;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_usuario", nullable = false)
    private UsuarioRole role;

    @NotNull
    @Column(name = "cpf", length = 11, unique = true, nullable = false)
    private String cpf;

    @NotNull
    @Column(name = "dt_nascimento", nullable = false)
    private Instant dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 10, nullable = false)
    private Genero genero;

    @Column(name = "nr_telefone", length = 13, nullable = false)
    private String telefone;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Formacao> formacoes;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Candidatura> candidaturas;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UsuarioSkill> usuarioSkills;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override
    public String getPassword() { return this.senhaHash; }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return this.deleted == 0; }
}