package desafio_foro.beta.dto;

import desafio_foro.beta.model.Topico;
import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime creadoEn,
        String estado,
        String autor,
        String curso
) {
    public DatosListadoTopico(Topico t) {
        this(t.getId(), t.getTitulo(), t.getMensaje(), t.getCreadoEn(),
                t.getEstado(), t.getAutor(), t.getCurso());
    }
}
