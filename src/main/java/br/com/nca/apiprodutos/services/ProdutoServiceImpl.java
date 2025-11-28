package br.com.nca.apiprodutos.services;

import br.com.nca.apiprodutos.dtos.ProdutoRequest;
import br.com.nca.apiprodutos.dtos.ProdutoResponse;
import br.com.nca.apiprodutos.entities.Produto;
import br.com.nca.apiprodutos.interfaces.ProdutoService;
import br.com.nca.apiprodutos.repositories.ProdutoRepository;
import br.com.nca.apiprodutos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ProdutoResponse criar(ProdutoRequest produtoRequest, String userName) {
        var usuario = usuarioRepository.findByLogin(userName)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));

        var produto = new Produto();
        produto.setNome(produtoRequest.nome());
        produto.setPreco(BigDecimal.valueOf(produtoRequest.preco()));
        produto.setQuantidade(produtoRequest.quantidade());
        produto.setUsuarioCriacao(usuario);

        produtoRepository.save(produto);

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco().doubleValue(),
                produto.getQuantidade());
    }

    @Override
    public ProdutoResponse alterar(UUID id, ProdutoRequest produtoRequest, String userName) {

        var usuario = usuarioRepository.findByLogin(userName)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if(produtoRequest.nome() != null) produto.setNome(produtoRequest.nome());
        if(produtoRequest.preco() != null) produto.setPreco(BigDecimal.valueOf(produtoRequest.preco()));
        if(produtoRequest.quantidade() != null) produto.setQuantidade(produtoRequest.quantidade());

        produto.setDataHoraUltimaAlteracao(LocalDateTime.now());
        produto.setUsuarioAlteracao(usuario);

        produtoRepository.save(produto);
        return toResponse(produto);
    }

    @Override
    public ProdutoResponse excluir(UUID id, String userName) {

        var usuario = usuarioRepository.findByLogin(userName)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido."));

        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        produto.softDelete(usuario);

        produtoRepository.save(produto);

        return toResponse(produto);
    }

    @Override
    public Page<ProdutoResponse> consultar(int page, int size) {
        if(page >= 1) page--;
        if(size > 25) throw new IllegalArgumentException("A consulta deve ser de no máximo 25 produtos");

        var pageable = PageRequest.of(page, size);

        var produtos = produtoRepository.findAll(pageable);

        var responseList = produtos.stream()
                .map(this::toResponse)
                .toList();

        return new PageImpl<>(responseList,pageable,produtos.getTotalElements());
    }

    @Override
    public ProdutoResponse obterPorId(UUID id) {

        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        return toResponse(produto);
    }

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco().doubleValue(),
                produto.getQuantidade()
        );
    }
}
