package br.com.nca.apiprodutos;

import br.com.nca.apiprodutos.components.CryptoComponent;
import br.com.nca.apiprodutos.components.JwtBearerTokenComponent;
import br.com.nca.apiprodutos.dtos.AutenticarRequest;
import br.com.nca.apiprodutos.entities.Usuario;
import br.com.nca.apiprodutos.repositories.UsuarioRepository;
import br.com.nca.apiprodutos.services.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceUnitTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    CryptoComponent cryptoComponent;

    @Mock
    JwtBearerTokenComponent jwtBearerComponent;

    UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setup() {
        usuarioService = new UsuarioServiceImpl(
                usuarioRepository, cryptoComponent, jwtBearerComponent);
    }

    @Test
    @DisplayName("Deve autenticar um usuário com sucesso.")
    void deveAutenticarUmUsuarioComSucesso() {

        var request = new AutenticarRequest("admin", "123");

        var senhaCriptografada = "ABC123";
        when(cryptoComponent.getSHA256("123")).thenReturn(senhaCriptografada);

        var usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setLogin("admin");

        when(usuarioRepository.find("admin", senhaCriptografada))
                .thenReturn(Optional.of(usuario));

        when(jwtBearerComponent.getExpiration())
                .thenReturn(Date.from(Instant.now().plusSeconds(3600)));

        when(jwtBearerComponent.generateToken("admin", "usuario"))
                .thenReturn("TOKEN-123");

        var response = usuarioService.autenticar(request);

        assertNotNull(response);
        assertEquals(usuario.getId(), response.id());
        assertEquals("admin", response.login());
        assertNotNull(response.dataHoraAcesso());
        assertNotNull(response.dataHoraExpiracao());
        assertEquals("TOKEN-123", response.accessToken());
    }

    @Test
    @DisplayName("Deve lançar exceção quando acesso negado.")
    void deveLancarExcecaoQuandoAcessoNegado() {

        var request = new AutenticarRequest("admin", "123");

        when(cryptoComponent.getSHA256("123")).thenReturn("XYZ");
        when(usuarioRepository.find("admin", "XYZ"))
                .thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.autenticar(request));

        assertEquals("Acesso negado", exception.getMessage());
    }
}