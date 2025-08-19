package desafio_foro.beta.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // Aquí puedes inspeccionar el mensaje de error de MySQL si quieres más detalle
        String mensaje = "El correo ya está registrado. Intenta con otro.";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mensaje);
    }
}
