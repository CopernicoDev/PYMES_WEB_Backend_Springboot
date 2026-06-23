package com.pymes.backend.nicolas.pymes_web_backend_springboot.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador Global de Excepciones para toda la API REST.
 * 
 * @ControllerAdvice hace que esta clase escuche todas las excepciones
 *                   lanzadas por cualquier @RestController de la aplicación.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura los errores de validación de los DTOs (cuando falla un @Valid).
     * 
     * En lugar de devolver la traza de error por defecto de Spring (que es enorme
     * y difícil de procesar por el frontend), devolvemos un JSON limpio.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // Extraemos los errores de campo específicos
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        // Construimos nuestra respuesta JSON personalizada
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de Validación");
        response.put("message", "Algunos campos no cumplen con los requisitos");
        response.put("errors", fieldErrors); // Aquí metemos la lista de campos con error

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Opcional: Capturar otras excepciones genéricas (errores inesperados).
     * Esto evita que se muestre el stacktrace completo si algo explota en el
     * servidor.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalExceptions(Exception ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error Interno del Servidor");
        response.put("message", "Ha ocurrido un error inesperado. Contacte con el administrador.");
        // Nunca devolvemos ex.getMessage() al frontend por seguridad en un error 500

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
