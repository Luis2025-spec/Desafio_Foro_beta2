package desafio_foro.beta.controller;

import desafio_foro.beta.dto.DatosDetalleTopico;
import desafio_foro.beta.dto.DatosListadoTopico;
import desafio_foro.beta.dto.DatosRegistroTopico;
import desafio_foro.beta.model.Topico;
import desafio_foro.beta.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping({"/topicos", "/tópicos"})
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    // CREAR
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroTopico datos) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.status(409)
                    .body("Ya existe un tópico con el mismo título y mensaje.");
        }
        try {
            Topico guardado = topicoRepository.save(
                    new Topico(datos.titulo(), datos.mensaje(), datos.autor(), datos.curso())
            );
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(guardado.getId()).toUri();
            return ResponseEntity.created(location).body(guardado);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409)
                    .body("Tópico duplicado (título + mensaje debe ser único).");
        }
    }

    // LISTAR con filtros opcionales + paginación (ÚNICO @GetMapping raíz)
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<DatosListadoTopico>> listar(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "creadoEn", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        LocalDateTime inicio = null;
        LocalDateTime fin = null;

        if (anio != null) {
            inicio = LocalDateTime.of(anio, 1, 1, 0, 0);
            fin = inicio.plusYears(1);
        }

        Page<DatosListadoTopico> page = topicoRepository
                .buscarPorCursoYRango(curso, inicio, fin, pageable)
                .map(DatosListadoTopico::new);

        return ResponseEntity.ok(page);
    }

    // LISTAR TODOS (sin paginar) en otra ruta para evitar conflicto
    @GetMapping(path = "/todos", produces = "application/json")
    public ResponseEntity<List<DatosListadoTopico>> listarTodos() {
        var lista = topicoRepository.findAll(Sort.by(Sort.Direction.ASC, "creadoEn"))
                .stream().map(DatosListadoTopico::new).toList();
        return ResponseEntity.ok(lista);
    }
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<DatosDetalleTopico> detalle(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(t -> ResponseEntity.ok(new DatosDetalleTopico(t)))
                .orElse(ResponseEntity.notFound().build());
    }



    // ACTUALIZAR
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid DatosRegistroTopico datos) {
        return topicoRepository.findById(id)
                .map(existing -> {
                    boolean cambiaTituloMensaje =
                            !existing.getTitulo().equals(datos.titulo()) ||
                                    !existing.getMensaje().equals(datos.mensaje());

                    if (cambiaTituloMensaje &&
                            topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
                        return ResponseEntity.status(409)
                                .body("Ya existe un tópico con el mismo título y mensaje.");
                    }

                    existing.setTitulo(datos.titulo());
                    existing.setMensaje(datos.mensaje());
                    existing.setAutor(datos.autor());
                    existing.setCurso(datos.curso());

                    try {
                        Topico actualizado = topicoRepository.save(existing);
                        return ResponseEntity.ok(actualizado);
                    } catch (DataIntegrityViolationException e) {
                        return ResponseEntity.status(409)
                                .body("Tópico duplicado (título + mensaje debe ser único).");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // Verifica existencia (regla de negocio)
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }

        try {
            topicoRepository.deleteById(id); // eliminación “física”
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // Por si hay FK que impidan borrar (responder 409 si aplica a tu caso)
            return ResponseEntity.status(409).build();
        }
    }
    // OPCIONAL: primeros 10 por fecha de creación ASC
    @GetMapping("/primeros10")
    public ResponseEntity<List<DatosListadoTopico>> primeros10() {
        var lista = topicoRepository.findTop10ByOrderByCreadoEnAsc()
                .stream().map(DatosListadoTopico::new).toList();
        return ResponseEntity.ok(lista);
    }
}
