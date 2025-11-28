package br.com.nca.apiprodutos.dtos;

import java.util.UUID;

public record ProdutoResponse(
        UUID id,
        String nome,
        Double preco,
        Integer quantidade
) {
}
