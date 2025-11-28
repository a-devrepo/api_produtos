package br.com.nca.apiprodutos.controllers;

import br.com.nca.apiprodutos.components.JwtBearerTokenComponent;
import br.com.nca.apiprodutos.dtos.ProdutoRequest;
import br.com.nca.apiprodutos.interfaces.ProdutoService;
import br.com.nca.apiprodutos.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProdutosController {

    private final ProdutoService produtoService;
    private final JwtBearerTokenComponent jwtBearerTokenComponent;

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "25") int size) {
        return ResponseEntity.ok(produtoService.consultar(page, size));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ProdutoRequest produtoRequest, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(produtoService.criar(produtoRequest, getUserName(httpRequest)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable UUID id, @RequestBody ProdutoRequest produtoRequest
            , HttpServletRequest httpRequest) {
        return ResponseEntity.ok(produtoService.alterar(id, produtoRequest, getUserName(httpRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(produtoService.excluir(id, getUserName(httpRequest)));
    }

    private String getUserName(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("Token JWT ausente ou inválido.");
        }

        var token = authorization.replace("Bearer", "").trim();
        return jwtBearerTokenComponent.getUsernameFromToken(token);
    }
}
