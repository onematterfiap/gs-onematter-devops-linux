package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.QuestaoRequestDto;
import br.com.fiap.one_matter.model.Questao;
import br.com.fiap.one_matter.repository.QuestaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestaoService {

    private final QuestaoRepository questaoRepository;

    public Page<Questao> listar(Pageable pageable) {
        return questaoRepository.findAll(pageable);
    }

    public Questao buscarPorId(Long id) {
        return questaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Questão não encontrada."));
    }

    @Transactional
    public Questao criar(QuestaoRequestDto dto) {
        Questao q = Questao.builder()
                .nivelDificuldade(dto.nivelDificuldade())
                .enunciado(dto.enunciado())
                .alternativa1(dto.alternativa1())
                .alternativa2(dto.alternativa2())
                .alternativa3(dto.alternativa3())
                .alternativa4(dto.alternativa4())
                .alternativa5(dto.alternativa5())
                .build();
        return questaoRepository.save(q);
    }

    @Transactional
    public Questao atualizar(Long id, QuestaoRequestDto dto) {
        Questao q = buscarPorId(id);
        q.setNivelDificuldade(dto.nivelDificuldade());
        q.setEnunciado(dto.enunciado());
        q.setAlternativa1(dto.alternativa1());
        q.setAlternativa2(dto.alternativa2());
        q.setAlternativa3(dto.alternativa3());
        q.setAlternativa4(dto.alternativa4());
        q.setAlternativa5(dto.alternativa5());
        return q;
    }

    @Transactional
    public void deletar(Long id) {
        Questao q = buscarPorId(id);
        questaoRepository.delete(q); // (Não tem soft delete no diagrama)
    }
}