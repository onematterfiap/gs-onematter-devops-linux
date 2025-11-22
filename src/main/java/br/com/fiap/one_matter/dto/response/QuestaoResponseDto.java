package br.com.fiap.one_matter.dto.response;

import br.com.fiap.one_matter.model.Questao;

public record QuestaoResponseDto(
        Long id,
        Integer nivelDificuldade,
        String enunciado,
        String alternativa1,
        String alternativa2,
        String alternativa3,
        String alternativa4,
        String alternativa5
) {
    public static QuestaoResponseDto fromQuestao(Questao q) {
        return new QuestaoResponseDto(
                q.getId(),
                q.getNivelDificuldade(),
                q.getEnunciado(),
                q.getAlternativa1(),
                q.getAlternativa2(),
                q.getAlternativa3(),
                q.getAlternativa4(),
                q.getAlternativa5()
        );
    }
}