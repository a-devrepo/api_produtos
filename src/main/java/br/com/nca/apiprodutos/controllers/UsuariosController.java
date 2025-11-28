package br.com.nca.apiprodutos.controllers;

import br.com.nca.apiprodutos.dtos.AutenticarRequest;
import br.com.nca.apiprodutos.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuariosController {

    private final UsuarioService usuarioService;

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody AutenticarRequest request) {
        try{
            var response = usuarioService.autenticar(request);
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
