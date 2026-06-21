package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

// --- Importaciones estándar de Java ---
import java.util.List;
import java.util.Optional;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;

/**
 * Interfaz que define el CONTRATO de operaciones disponibles para Role.
 * 
 * ¿Por qué una interfaz y no directamente la clase?
 * - Desacoplamiento: el Controller depende del contrato, no de la implementación.
 * - Testeabilidad: en tests puedes crear un mock que implemente esta interfaz.
 * - Flexibilidad: podrías tener varias implementaciones (ej: una con caché).
 * 
 * Este servicio tiene más métodos que UserService y ServiceService porque
 * los roles necesitan búsquedas especiales (por nombre, por lista de nombres, etc.)
 * que se usan al asignar roles a usuarios.
 * 
 * Es el mismo patrón que UserService y ServiceService.
 */
public interface RoleService {

    // Busca un rol por su nombre exacto. Devuelve Optional porque puede no existir.
    // Ejemplo: findByRolname("ROLE_ADMIN")
    Optional<Role> findByRolname(String rolname);

    // Verifica si existe un rol con ese nombre, sin traer la entidad completa.
    // Más eficiente que findByRolname() cuando solo necesitas saber si existe.
    boolean existsByRolname(String rolname);

    // Devuelve todos los roles de la base de datos
    List<Role> findAll();

    // Busca múltiples roles cuyos nombres estén en la lista dada.
    // Útil para asignar varios roles a un usuario de golpe.
    // Ejemplo: findByRolnameIn(List.of("ROLE_ADMIN", "ROLE_USER"))
    List<Role> findByRolnameIn(List<String> rolnames);

    // Busca un rol por su ID
    Optional<Role> findById(Long id);

    // Guarda un rol nuevo o actualiza uno existente
    Role save(Role role);

    // Elimina un rol por su ID
    void deleteById(Long id);

    // Elimina un rol pasando el objeto completo
    void delete(Role role);

    // Elimina todos los roles
    void deleteAll();

}
