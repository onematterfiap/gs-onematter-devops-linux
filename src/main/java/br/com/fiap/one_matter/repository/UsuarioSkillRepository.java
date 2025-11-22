package br.com.fiap.one_matter.repository;

import br.com.fiap.one_matter.model.UsuarioSkill;
import br.com.fiap.one_matter.model.UsuarioSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioSkillRepository extends JpaRepository<UsuarioSkill, UsuarioSkillId> {
}