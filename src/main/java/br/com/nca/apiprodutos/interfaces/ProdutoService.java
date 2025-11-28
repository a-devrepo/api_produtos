package br.com.nca.apiprodutos.interfaces;

import br.com.nca.apiprodutos.dtos.ProdutoRequest;
import br.com.nca.apiprodutos.dtos.ProdutoResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProdutoService {

    ProdutoResponse criar(ProdutoRequest produtoRequest, String userName);

    ProdutoResponse alterar(UUID id, ProdutoRequest produtoRequest, String userName);

    ProdutoResponse excluir(UUID id,String userName);

    Page<ProdutoResponse> consultar(int page, int size);

    ProdutoResponse obterPorId(UUID id);
}
