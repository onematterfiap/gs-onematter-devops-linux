package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.TesteRequestDto;
import br.com.fiap.one_matter.model.*;
import br.com.fiap.one_matter.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TesteService {

    private final TesteRepository testeRepository;
    private final TesteQuestaoRepository testeQuestaoRepository;
    private final VagaService vagaService;
    private final QuestaoService questaoService;
    private final CandidaturaRepository candidaturaRepository;

    public Teste buscarPorId(Long id) {
        return testeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teste não encontrado."));
    }

    @Transactional
    public Teste criar(TesteRequestDto dto) {
        Vaga vaga = vagaService.buscarPorId(dto.idVaga());
        Teste teste = Teste.builder()
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .vaga(vaga)
                .build();
        return testeRepository.save(teste);
    }

    @Transactional
    public void associarQuestao(Long idTeste, Long idQuestao) {
        Teste teste = buscarPorId(idTeste);
        Questao questao = questaoService.buscarPorId(idQuestao);
        TesteQuestaoId id = new TesteQuestaoId();
        id.setTesteId(teste.getId());
        id.setQuestaoId(questao.getId());
        testeQuestaoRepository.save(new TesteQuestao(id, teste, questao));
    }

    // MÉTODO QUE O ESP32 CHAMA
    @Transactional
    public String finalizarTeste(Long idCandidatura, Double score) {
        // 1. Valida se já fez
        Integer concluido = candidaturaRepository.verificarConclusao(idCandidatura);
        if (concluido == 1) {
            throw new IllegalStateException("Prova já finalizada.");
        }

        // 2. Chama Procedure que insere o status TESTE_SUBMETIDO e salva a nota
        candidaturaRepository.finalizarProvaDB(idCandidatura, score);

        // 3. Retorna feedback
        return candidaturaRepository.calcularSituacao(score);
    }

    @Transactional
    public List<Questao> buscarQuestoesDoTestePorCandidatura(Long idCandidatura) {
        // Busca candidatura para validar
        Candidatura candidatura = candidaturaRepository.findById(idCandidatura)
                .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada."));

        // Valida conclusão
        Integer concluido = candidaturaRepository.verificarConclusao(idCandidatura);
        if (concluido == 1) {
            throw new IllegalStateException("Acesso negado: Prova já concluída.");
        }

        // Registra inicio (status EM_ANDAMENTO)
        candidaturaRepository.registrarInicioProva(idCandidatura);

        Vaga vaga = candidatura.getVaga();
        Teste teste = testeRepository.findAll().stream()
                .filter(t -> t.getVaga().getId().equals(vaga.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Não há teste para esta vaga."));

        return teste.getTesteQuestoes().stream()
                .map(TesteQuestao::getQuestao)
                .toList();
    }
}