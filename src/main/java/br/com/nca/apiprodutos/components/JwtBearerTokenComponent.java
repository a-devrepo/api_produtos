package br.com.nca.apiprodutos.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtBearerTokenComponent {

    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public String generateToken(String user, String role) {
        return Jwts.builder()
                .setSubject(user)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(jwtSecretKey)
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpiration(){
        var dataAtual = new Date();
        return new Date(dataAtual.getTime() + jwtExpiration);
    }
}