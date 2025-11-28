package br.com.nca.apiprodutos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 25, nullable = false, unique = true)
    private String login;

    @Column(length = 100, nullable = false)
    private String senha;
}
