package br.com.nca.apiprodutos.services;

import br.com.nca.apiprodutos.components.CryptoComponent;
import br.com.nca.apiprodutos.components.JwtBearerTokenComponent;
import br.com.nca.apiprodutos.dtos.AutenticarRequest;
import br.com.nca.apiprodutos.dtos.AutenticarResponse;
import br.com.nca.apiprodutos.repositories.UsuarioRepository;
import br.com.nca.apiprodutos.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private  final CryptoComponent cryptoComponent;
    private final JwtBearerTokenComponent jwtBearerTokenComponent;

    @Override
    public AutenticarResponse autenticar(AutenticarRequest autenticarRequest) {
        var usuario = usuarioRepository
                .find(autenticarRequest.login(), cryptoComponent.getSHA256(autenticarRequest.senha()))
                .orElseThrow(() -> new IllegalArgumentException("Acesso negado"));

        return new AutenticarResponse(usuario.getId(),
                usuario.getLogin(),
                LocalDateTime.now(),
                jwtBearerTokenComponent.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                jwtBearerTokenComponent.generateToken(usuario.getLogin(), "usuario")
        );
    }
}
