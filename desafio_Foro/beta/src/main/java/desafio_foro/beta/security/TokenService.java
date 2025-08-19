// desafio_foro.beta.security.TokenService
package desafio_foro.beta.security;

import desafio_foro.beta.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}") // en milisegundos, ej 7200000 = 2h
    private long expiration;

    public String generarToken(Usuario user) {
        Algorithm alg = Algorithm.HMAC256(secret);
        long now = System.currentTimeMillis();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + expiration))
                .sign(alg);
    }
}
