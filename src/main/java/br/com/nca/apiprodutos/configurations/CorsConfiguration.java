package br.com.nca.apiprodutos.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // libera todos os endpoints
                        .allowedOrigins("*") // libera qualquer origem
                        .allowedMethods("*") // libera todos os métodos (GET, POST, PUT, PATCH, DELETE)
                        .allowedHeaders("*") // libera todos os headers
                        .allowCredentials(false); // false porque "*" não permite credenciais
            }
        };
    }
}
