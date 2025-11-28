package br.com.nca.apiprodutos;

import br.com.nca.apiprodutos.components.CryptoComponent;
import br.com.nca.apiprodutos.dtos.AutenticarRequest;
import br.com.nca.apiprodutos.entities.Usuario;
import br.com.nca.apiprodutos.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UsuarioControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CryptoComponent cryptoComponent;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll();

        var usuario = new Usuario();
        usuario.setLogin("admin");
        usuario.setSenha(cryptoComponent.getSHA256("ABC123"));
        usuarioRepository.save(usuario);
    }

    private String url() {
        return "http://localhost:" + port + "/api/v1/usuarios/autenticar";
    }

    @Test
    @DisplayName("Autenticar deve retornar OK quando usuário existe.")
    void autenticar_DeveRetornarOk_QuandoUsuarioExiste() {
        var request = new AutenticarRequest("admin", "ABC123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AutenticarRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url(), HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("accessToken"));
    }

    @Test
    @DisplayName("Autenticar retornar 401 quando usuário inválido.")
    void autenticar_DeveRetornar401_QuandoUsuarioInvalido() {
        var request = new AutenticarRequest("usuariofake", "123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AutenticarRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url(), HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
