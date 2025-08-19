package desafio_foro.beta.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosLogin(
        @NotBlank String login,
        @NotBlank String password
) {}