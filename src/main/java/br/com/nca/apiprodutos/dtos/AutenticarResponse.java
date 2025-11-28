package br.com.nca.apiprodutos.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record AutenticarResponse(
        UUID id,
        String login,
        LocalDateTime dataHoraAcesso,
        LocalDateTime dataHoraExpiracao,
        String accessToken
) {
}
