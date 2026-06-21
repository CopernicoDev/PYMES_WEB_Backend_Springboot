package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

// --- Importaciones estándar de Java ---
import java.util.List;
import java.util.Optional;

// @Service marca esta clase como un componente de Spring (un "bean").
// Spring la detectará automáticamente y la inyectará donde se necesite.
import org.springframework.stereotype.Service;

// @Transactional gestiona las transacciones de base de datos automáticamente.
// Si algo falla dentro del método, hace rollback (deshace los cambios).
import org.springframework.transaction.annotation.Transactional;

// --- Importaciones de nuestro proyecto ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.RoleRepository;

//Importación de Spring Data
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Implementación concreta de RoleService.
 * 
 * Esta clase contiene la lógica de negocio para los roles.
 * Por ahora es simple (delega al repositorio), pero aquí es donde
 * irían reglas de negocio como:
 * - "No se puede crear un rol con un nombre que ya existe"
 * - "No se puede eliminar ROLE_ADMIN si es el único admin del sistema"
 * - "Los nombres de roles deben empezar con ROLE_"
 * 
 * Es el mismo patrón que UserServiceImpl y ServiceServiceImpl.
 */
@Service // Le dice a Spring: "esta clase es un servicio, regístrala como bean"
public class RoleServiceImpl implements RoleService {

    // Inyección por constructor (mismo patrón que UserServiceImpl).
    // "final" garantiza que no se puede cambiar después de la construcción.
    private final RoleRepository repository;

    // Spring ve que RoleServiceImpl necesita un RoleRepository
    // y se lo pasa automáticamente por el constructor. Sin @Autowired.
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca un rol por su nombre exacto.
     * Delega al Query Method findByRolname() del repositorio.
     * 
     * @Transactional(readOnly = true): optimiza la consulta para solo lectura.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByRolname(String rolname) {
        return repository.findByRolname(rolname);
    }

    /**
     * Verifica si existe un rol con ese nombre.
     * Más eficiente que findByRolname() porque no trae la entidad completa,
     * solo ejecuta un COUNT.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByRolname(String rolname) {
        return repository.existsByRolname(rolname);
    }

    /**
     * Obtiene TODOS los roles de la base de datos.
     * El cast a (List<Role>) es necesario porque CrudRepository.findAll()
     * devuelve Iterable, pero RoleRepository sobreescribe findAll()
     * para devolver List directamente.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return (List<Role>) repository.findAll();
    }

    /**
     * Busca múltiples roles cuyos nombres estén en la lista dada.
     * Útil cuando el frontend envía una lista de roles para asignar a un usuario.
     * El repositorio genera: SELECT * FROM roles WHERE rolname IN (?, ?, ?)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> findByRolnameIn(List<String> rolnames) {
        return repository.findByRolnameIn(rolnames);
    }

    /**
     * Guarda un rol en la base de datos.
     * Si el rol tiene ID → actualiza (UPDATE).
     * Si no tiene ID → crea uno nuevo (INSERT).
     * 
     * @Transactional sin readOnly porque MODIFICA datos.
     */
    @Override
    @Transactional
    public Role save(Role role) {
        return repository.save(role);
    }

    /**
     * Elimina un rol por su ID.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Elimina un rol pasando el objeto completo.
     * Útil cuando ya tienes la entidad cargada en memoria.
     */
    @Override
    @Transactional
    public void delete(Role role) {
        repository.delete(role);
    }

    /**
     * Elimina TODOS los roles. Cuidado con este método en producción.
     */
    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * Busca un rol por ID.
     * Devuelve Optional.empty() si no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Busca roles de forma paginada.
     * Spring genera automáticamente las queries para paginación.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Role> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
