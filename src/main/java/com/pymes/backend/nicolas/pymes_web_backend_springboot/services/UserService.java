package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

// --- Importaciones estándar de Java ---
import java.util.List;
import java.util.Optional;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

/**
 * Interfaz que define el CONTRATO de operaciones disponibles para User.
 * 
 * ¿Por qué una interfaz y no directamente la clase?
 * - Desacoplamiento: el Controller depende del contrato, no de la implementación.
 *   Si mañana cambias la implementación (ej: añades caché), el Controller
 *   no se entera porque sigue usando la misma interfaz.
 * - Testeabilidad: en tests puedes crear un mock que implemente esta interfaz
 *   sin necesidad de una BD real.
 * - Principio de Inversión de Dependencias (SOLID): las clases de alto nivel
 *   (Controller) dependen de abstracciones (interfaz), no de detalles (implementación).
 * 
 * Es el mismo patrón que ServiceService y RoleService.
 */
public interface UserService {

    // Devuelve todos los usuarios de la base de datos como List
    List<User> findAllUsers();

    // Busca un usuario por su ID. Devuelve Optional porque puede no existir.
    Optional<User> findById(Long id);

    // Guarda un usuario nuevo o actualiza uno existente. Devuelve el usuario guardado.
    User save(User user);

    // Elimina un usuario por su ID
    void deleteById(Long id);

    // Elimina todos los usuarios
    void deleteAll();

}
