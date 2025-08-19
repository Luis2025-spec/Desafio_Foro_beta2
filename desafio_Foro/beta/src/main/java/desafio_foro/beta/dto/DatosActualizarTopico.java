package desafio_foro.beta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosActualizarTopico(
        @NotBlank
        @Size(max = 255)
        String titulo,

        @NotBlank
        @Size(max = 1000)
        String mensaje,

        @NotBlank
        @Size(max = 100)
        String autor,

        @NotBlank
        @Size(max = 100)
        String curso
) {}
