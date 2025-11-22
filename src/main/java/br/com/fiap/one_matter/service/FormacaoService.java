package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.FormacaoRequestDto;
import br.com.fiap.one_matter.model.Formacao;
import br.com.fiap.one_matter.model.Usuario;
import br.com.fiap.one_matter.repository.FormacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormacaoService {

    private final FormacaoRepository formacaoRepository;

    public List<Formacao> listarDoCandidato(Long idCandidato) {
        return formacaoRepository.findByCandidatoId(idCandidato);
    }

    public Formacao buscarPorId(Long id, Long idCandidato) {
        return formacaoRepository.findByIdAndCandidatoId(id, idCandidato)
                .orElseThrow(() -> new EntityNotFoundException("Formação não encontrada ou não pertence ao usuário."));
    }

    @Transactional
    public Formacao criar(FormacaoRequestDto dto, Usuario candidatoLogado) {
        Formacao formacao = Formacao.builder()
                .nomeFormacao(dto.nomeFormacao())
                .descricao(dto.descricao())
                .instituicao(dto.instituicao())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .candidato(candidatoLogado)
                .build();

        return formacaoRepository.save(formacao);
    }

    @Transactional
    public void deletar(Long id, Long idCandidato) {
        Formacao formacao = buscarPorId(id, idCandidato); // Valida se pertence ao usuário
        formacaoRepository.delete(formacao);
    }
}