package br.com.nca.apiprodutos.interfaces;

import br.com.nca.apiprodutos.dtos.AutenticarRequest;
import br.com.nca.apiprodutos.dtos.AutenticarResponse;

public interface UsuarioService {

    AutenticarResponse autenticar(AutenticarRequest autenticarRequest);
}
