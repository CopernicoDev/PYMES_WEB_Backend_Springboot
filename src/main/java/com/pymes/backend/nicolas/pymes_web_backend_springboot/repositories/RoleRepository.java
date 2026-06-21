package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

// --- Importaciones estándar de Java ---
import java.util.List;
import java.util.Optional;

// --- Importación de Spring Data ---
import org.springframework.data.repository.CrudRepository;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;

/**
 * Repositorio para acceder a la tabla "roles" en la base de datos.
 * 
 * Extiende CrudRepository<Role, Long> donde:
 * - Role: la entidad JPA que gestiona este repositorio.
 * - Long: el tipo de dato de la clave primaria (id) de Role.
 * 
 * Además de los métodos heredados de CrudRepository (save, findById, deleteAll, etc.),
 * este repositorio define QUERY METHODS personalizados.
 * 
 * ¿Qué son los Query Methods?
 * Spring Data genera automáticamente la consulta SQL analizando el NOMBRE del método.
 * Por ejemplo: findByRolname(String rolname) → SELECT * FROM roles WHERE rolname = ?
 * No necesitas escribir SQL ni implementar estos métodos manualmente.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     * Busca un rol por su nombre exacto.
     * Spring genera: SELECT * FROM roles WHERE rolname = ?
     * Devuelve Optional porque el rol puede no existir.
     * 
     * Ejemplo: findByRolname("ROLE_ADMIN") → busca el rol de administrador.
     */
    Optional<Role> findByRolname(String rolname);

    /**
     * Verifica si existe un rol con ese nombre, sin traer toda la entidad.
     * Spring genera: SELECT COUNT(*) > 0 FROM roles WHERE rolname = ?
     * Devuelve true/false. Más eficiente que findByRolname() si solo necesitas
     * saber si existe (no necesitas los datos del rol).
     */
    boolean existsByRolname(String rolname);

    /**
     * Devuelve TODOS los roles como List (no como Iterable).
     * Sobreescribe el findAll() de CrudRepository para devolver List directamente,
     * lo cual es más cómodo para trabajar con streams y transformaciones.
     */
    List<Role> findAll();

    /**
     * Busca múltiples roles cuyos nombres estén en la lista dada.
     * Spring genera: SELECT * FROM roles WHERE rolname IN (?, ?, ?)
     * 
     * Útil para asignar varios roles a un usuario de golpe.
     * Ejemplo: findByRolnameIn(List.of("ROLE_ADMIN", "ROLE_USER"))
     */
    List<Role> findByRolnameIn(List<String> rolnames);

    /**
     * Busca un rol por su ID. Heredado de CrudRepository,
     * aquí se declara explícitamente por claridad.
     */
    Optional<Role> findById(Long id);

    /**
     * Elimina un rol por su ID. Heredado de CrudRepository,
     * aquí se declara explícitamente por claridad.
     */
    void deleteById(Long id);

    /**
     * Elimina TODOS los roles. Heredado de CrudRepository,
     * aquí se declara explícitamente por claridad.
     */
    void deleteAll();

}
