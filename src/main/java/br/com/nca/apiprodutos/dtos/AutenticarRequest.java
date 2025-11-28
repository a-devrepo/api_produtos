package br.com.nca.apiprodutos.dtos;

public record AutenticarRequest(
        String login,
        String senha
) {
}
