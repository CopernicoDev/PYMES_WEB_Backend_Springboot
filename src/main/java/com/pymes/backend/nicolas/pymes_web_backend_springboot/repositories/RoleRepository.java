package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

// --- Importaciones estándar de Java ---
import java.util.List;
import java.util.Optional;

// --- Importación de Spring Data ---
// Migramos a JpaRepository para tener paginación y List en findAll()
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;

/**
 * Repositorio para acceder a la tabla "roles" en la base de datos.
 * 
 * CAMBIO: Migramos de CrudRepository a JpaRepository.
 * 
 * Role NO necesita JOIN FETCH porque no tiene relaciones LAZY que se
 * accedan en los DTOs. La relación users está marcada con @JsonIgnore
 * y nunca se accede al convertir a RoleResponseDto.
 * 
 * Todos los Query Methods existentes siguen funcionando igual.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Busca un rol por su nombre exacto.
     * Spring genera: SELECT * FROM roles WHERE rolname = ?
     */
    Optional<Role> findByRolname(String rolname);

    /**
     * Verifica si existe un rol con ese nombre, sin traer toda la entidad.
     * Spring genera: SELECT COUNT(*) > 0 FROM roles WHERE rolname = ?
     */
    boolean existsByRolname(String rolname);

    /**
     * Busca múltiples roles cuyos nombres estén en la lista dada.
     * Spring genera: SELECT * FROM roles WHERE rolname IN (?, ?, ?)
     */
    List<Role> findByRolnameIn(List<String> rolnames);

    /**
     * VERSIÓN PAGINADA: Trae roles de forma paginada.
     * GET /api/roles?page=0&size=10
     */
    Page<Role> findAll(Pageable pageable);
}
