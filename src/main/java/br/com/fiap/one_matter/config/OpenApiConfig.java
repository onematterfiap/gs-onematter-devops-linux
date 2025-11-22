package br.com.fiap.one_matter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API One Matter - Global Solution 2TDS 2025 2° Semestre ")
                        .description("Documentação do projeto One Matter - Contratações Justas.")
                        .version("v1.0.0"));
    }
}