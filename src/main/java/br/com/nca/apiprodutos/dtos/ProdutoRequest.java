package br.com.nca.apiprodutos.dtos;

public record ProdutoRequest(
        String nome,
        Double preco,
        Integer quantidade
) {
}
