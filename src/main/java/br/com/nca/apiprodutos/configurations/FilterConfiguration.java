package br.com.nca.apiprodutos.configurations;

import br.com.nca.apiprodutos.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Value("${jwt.secretkey}")
    private String jwtSecretkey;

    @Bean
    FilterRegistrationBean<AuthenticationFilter> authenticationFilterConfig() {

        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(new AuthenticationFilter(jwtSecretkey));

        registration.addUrlPatterns("/api/v1/produtos/*");

        return registration;
    }
}

