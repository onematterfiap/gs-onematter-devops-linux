package br.com.fiap.one_matter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_ONEM_TESTE_QUESTAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TesteQuestao {

    @EmbeddedId
    private TesteQuestaoId id; // Agora referencia a classe pública

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("testeId")
    @JoinColumn(name = "id_teste")
    private Teste teste;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("questaoId")
    @JoinColumn(name = "id_questao")
    private Questao questao;
}