package br.com.nca.apiprodutos.components;

import br.com.nca.apiprodutos.entities.Usuario;
import br.com.nca.apiprodutos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoaderComponent implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CryptoComponent cryptoComponent;

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.count() == 0) {
            Usuario usuario = new Usuario();
            usuario.setLogin("administrador");
            usuario.setSenha(cryptoComponent.getSHA256("@Admin2025"));

            usuarioRepository.save(usuario);
        }
    }
}
