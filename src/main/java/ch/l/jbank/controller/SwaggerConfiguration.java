package ch.l.jbank.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("JBank")
                .version("1.0.0")
                .description("API backend desenvolvida com Spring Boot para gestão de carteiras bancárias. " +
                        "O sistema permite a criação e encerramento de carteiras, realização de depósitos, " +
                        "transferências entre contas e consulta de extratos detalhados. " +
                        "Implementa controle de concorrência com Optimistic Locking (@Version), " +
                        "transações ACID, auditoria via Interceptors e filtros para captura de IP."
                )
        );
    }
}