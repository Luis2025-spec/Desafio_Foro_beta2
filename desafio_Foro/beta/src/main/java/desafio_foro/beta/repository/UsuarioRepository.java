package desafio_foro.beta.repository;

import desafio_foro.beta.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aquí puedes agregar consultas personalizadas si las necesitas más adelante
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByLogin(String login);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
}
