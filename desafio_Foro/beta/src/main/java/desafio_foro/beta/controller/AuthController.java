// desafio_foro.beta.controller.AuthController
package desafio_foro.beta.controller;

import desafio_foro.beta.dto.DatosLogin;
import desafio_foro.beta.dto.DatosTokenJWT;
import desafio_foro.beta.model.Usuario;
import desafio_foro.beta.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DatosTokenJWT> login(@RequestBody @Valid DatosLogin datos) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(datos.login(), datos.password())
            );
            Usuario user = (Usuario) auth.getPrincipal();
            String jwt = tokenService.generarToken(user);
            return ResponseEntity.ok(new DatosTokenJWT(jwt, "Bearer"));
        } catch (AuthenticationException ex) {
            // ¡OJO! Hay que DEVOLVER algo; si solo llamas a ResponseEntity.status(...) sin return
            // caerás en “No body returned for response”.
            return ResponseEntity.status(401).build();
        }
    }
}
