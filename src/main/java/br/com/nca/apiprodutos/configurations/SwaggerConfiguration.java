package br.com.nca.apiprodutos.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Informe o token JWT no formato: Bearer {seu_token}");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("API Produtos")
                        .version("1.0.0")
                        .description("API para gerenciamento de produtos, com autenticação JWT.\n" +
                                "Projeto desenvolvido no curso Java Avançado – Formação Arquiteto.")
                        .contact(new Contact()
                                .name("Alisson Cruz")
                                .email("adevpr11@gmail.com")
                                .url("github.com/a-devrepo")
                        )
                )
                .addSecurityItem(securityRequirement)
                .schemaRequirement("bearerAuth", bearerAuthScheme);
    }
}