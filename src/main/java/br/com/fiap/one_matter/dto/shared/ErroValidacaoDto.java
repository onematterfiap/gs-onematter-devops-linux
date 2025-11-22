package br.com.fiap.one_matter.dto.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroValidacaoDto {
    private String campo;
    private String mensagem;
}