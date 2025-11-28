package br.com.nca.apiprodutos.repositories;

import br.com.nca.apiprodutos.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query("""
                SELECT u FROM Usuario u
                WHERE u.login = :login
                AND u.senha = :senha
            """)
    Optional<Usuario>find(@Param("login") String login, @Param("senha") String senha);

    Optional<Usuario> findByLogin(String login);
}
