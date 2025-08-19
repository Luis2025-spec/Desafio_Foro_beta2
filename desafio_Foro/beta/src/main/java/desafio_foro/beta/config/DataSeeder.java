// src/main/java/desafio_foro/beta/config/DataSeeder.java
package desafio_foro.beta.config;

import desafio_foro.beta.model.Usuario;
import desafio_foro.beta.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (!repo.existsByLogin("admin")) {
            Usuario u = new Usuario();
            u.setLogin("admin");
            u.setPassword(encoder.encode("admin123"));
            u.setNombre("Admin");
            u.setEmail("admin@demo.com");
            u.setRol("ROLE_USER");
            repo.save(u);
        }
    }
}
