package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.SkillRequestDto;
import br.com.fiap.one_matter.model.Skill;
import br.com.fiap.one_matter.model.Vaga;
import br.com.fiap.one_matter.model.VagaSkill;
import br.com.fiap.one_matter.model.VagaSkillId;
import br.com.fiap.one_matter.repository.SkillRepository;
import br.com.fiap.one_matter.repository.VagaSkillRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final VagaSkillRepository vagaSkillRepository;
    private final VagaService vagaService; // Reuso

    public Page<Skill> listar(Optional<Integer> deletedStatus, Pageable pageable) {
        if (deletedStatus.isPresent() && (deletedStatus.get() == 0 || deletedStatus.get() == 1)) {
            return skillRepository.findByDeleted(deletedStatus.get(), pageable);
        }
        return skillRepository.findAll(pageable);
    }

    public Skill buscarPorIdAtiva(Long id) {
        return skillRepository.findByIdAndDeleted(id, 0)
                .orElseThrow(() -> new EntityNotFoundException("Skill não encontrada ou inativa."));
    }

    @Transactional
    public Skill criar(SkillRequestDto dto) {
        Skill skill = Skill.builder()
                .nome(dto.nome())
                .categoria(dto.categoria())
                .deleted(0)
                .build();
        return skillRepository.save(skill);
    }

    @Transactional
    public Skill atualizar(Long id, SkillRequestDto dto) {
        Skill skill = buscarPorIdAtiva(id);
        skill.setNome(dto.nome());
        skill.setCategoria(dto.categoria());
        return skill;
    }

    @Transactional
    public void deletar(Long id) {
        Skill skill = buscarPorIdAtiva(id);
        skill.setDeleted(1);
        skillRepository.save(skill);
    }

    @Transactional
    public VagaSkill associarVaga(Long idSkill, Long idVaga) {
        Skill skill = buscarPorIdAtiva(idSkill);
        Vaga vaga = vagaService.buscarPorId(idVaga); // (VagaService já filtra por ativo)

        VagaSkillId associacaoId = new VagaSkillId();
        associacaoId.setSkillId(skill.getId());
        associacaoId.setVagaId(vaga.getId());

        // (Idealmente, verificar se a associação já existe)

        VagaSkill associacao = new VagaSkill(associacaoId, vaga, skill);
        return vagaSkillRepository.save(associacao);
    }
}