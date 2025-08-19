package desafio_foro.beta.repository;

import desafio_foro.beta.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Para verificar/obtener duplicados por (titulo, mensaje)
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    // Primeros 10 por fecha de creación ASC
    List<Topico> findTop10ByOrderByCreadoEnAsc();

    // Búsqueda con filtros opcionales: curso (contiene, case-insensitive) + año (rango)
    @Query(
            "SELECT t FROM Topico t " +
                    "WHERE (:curso IS NULL OR LOWER(t.curso) LIKE LOWER(CONCAT('%', :curso, '%'))) " +
                    "AND (:inicio IS NULL OR t.creadoEn >= :inicio) " +
                    "AND (:fin IS NULL OR t.creadoEn < :fin)"
    )
    Page<Topico> buscarPorCursoYRango(
            @Param("curso") String curso,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin,
            Pageable pageable
    );
    // TopicoRepository.java
    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);

}
