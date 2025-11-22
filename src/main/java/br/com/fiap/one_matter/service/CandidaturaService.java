// one-matter-api/src/main/java/br/com/fiap/one_matter/service/CandidaturaService.java
package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.model.Candidatura;
import br.com.fiap.one_matter.model.StatusCandidatura;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.model.Vaga;
import br.com.fiap.one_matter.repository.CandidaturaRepository;
import br.com.fiap.one_matter.repository.StatusCandidaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidaturaService {

    private final CandidaturaRepository candidaturaRepository;
    private final StatusCandidaturaRepository statusRepository;
    private final VagaService vagaService;

    @Transactional
    public Candidatura candidatar(Long idVaga, Usuario candidatoLogado) {
        Vaga vaga = vagaService.buscarPorId(idVaga);

        Optional<Candidatura> existente = candidaturaRepository.findByCandidatoIdAndVagaIdAndDeleted(
                candidatoLogado.getId(), idVaga, 0);

        if (existente.isPresent()) {
            throw new DataIntegrityViolationException("Candidato já cadastrado nesta vaga.");
        }

        Candidatura novaCandidatura = Candidatura.builder()
                .candidato(candidatoLogado)
                .vaga(vaga)
                .deleted(0)
                .build();

        candidaturaRepository.save(novaCandidatura);

        StatusCandidatura statusInicial = StatusCandidatura.builder()
                .candidatura(novaCandidatura)
                .statusDescricao("CANDIDATURA_REALIZADA")
                .build();

        statusRepository.save(statusInicial);

        novaCandidatura.setStatusHistorico(List.of(statusInicial));
        return novaCandidatura;
    }

    // --- NOVO MÉTODO PARA DETALHES ---
    @Transactional(readOnly = true)
    public Candidatura buscarDetalhesPorId(Long id) {
        Candidatura candidatura = candidaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada."));

        // Força o carregamento das coleções LAZY antes de retornar
        candidatura.getStatusHistorico().size();
        return candidatura;
    }
    // ---------------------------------

    public Page<Candidatura> listarMinhasCandidaturas(Usuario candidatoLogado, Pageable pageable) {
        return candidaturaRepository.findByCandidatoIdAndDeleted(candidatoLogado.getId(), 0, pageable);
    }

    public Page<Candidatura> listarCandidatosDaVaga(Long idVaga, Pageable pageable) {
        return candidaturaRepository.findByVagaIdAndDeleted(idVaga, 0, pageable);
    }

    @Transactional
    public void cancelar(Long idCandidatura, Usuario candidatoLogado) {
        Candidatura candidatura = candidaturaRepository.findByIdAndDeleted(idCandidatura, 0)
                .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada."));

        if (!candidatura.getCandidato().getId().equals(candidatoLogado.getId())) {
            throw new SecurityException("Acesso negado. Candidatura não pertence ao usuário.");
        }

        candidatura.setDeleted(1);
        candidaturaRepository.save(candidatura);
    }
}