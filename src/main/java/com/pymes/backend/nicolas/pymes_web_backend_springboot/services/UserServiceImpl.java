package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

// --- Importaciones estándar de Java ---
import java.util.List;
import java.util.Optional;

// @Service marca esta clase como un componente de Spring (un "bean").
// Spring la detectará automáticamente y la inyectará donde se necesite.
// OJO: esta anotación @Service es de Spring (org.springframework.stereotype.Service),
// NO es nuestra entidad User del paquete models. Comparten tipo pero son cosas distintas.
import org.springframework.stereotype.Service;

// @Transactional gestiona las transacciones de base de datos automáticamente.
// Si algo falla dentro del método, hace rollback (deshace los cambios).
import org.springframework.transaction.annotation.Transactional;

// --- Importaciones de nuestro proyecto ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.UserRepository;

/**
 * Implementación concreta de UserService.
 * 
 * Esta clase contiene la lógica de negocio para los usuarios.
 * Por ahora es simple (delega al repositorio), pero aquí es donde
 * irían reglas de negocio como:
 * - "No se pueden crear dos usuarios con el mismo email"
 * - "La contraseña debe encriptarse antes de guardarla" (Spring Security)
 * - "Enviar un email de bienvenida al crear un usuario"
 * - "Solo un ADMIN puede eliminar usuarios"
 * 
 * Es el mismo patrón que ServiceServiceImpl y RoleServiceImpl.
 */
@Service // Le dice a Spring: "esta clase es un servicio, regístrala como bean"
public class UserServiceImpl implements UserService {

    // Inyección por constructor (mismo patrón que ServiceServiceImpl).
    // "final" garantiza que no se puede cambiar después de la construcción.
    private final UserRepository userRepository;

    // Spring ve que UserServiceImpl necesita un UserRepository
    // y se lo pasa automáticamente por el constructor. Sin @Autowired.
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Obtiene TODOS los usuarios de la base de datos.
     * 
     * @Transactional(readOnly = true) le dice a Hibernate:
     *     "esta operación solo LEE datos, no modifica nada".
     *     Esto permite a Hibernate optimizar la consulta
     *     (no necesita preparar un flush ni tracking de cambios).
     * 
     * Usa el método personalizado findAllUsers() del repositorio
     * que devuelve List directamente (en vez del Iterable de CrudRepository).
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAllUsers();
    }

    /**
     * Busca un usuario por ID.
     * Devuelve Optional.empty() si no existe, en vez de null
     * (evita NullPointerException en el Controller).
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Guarda un usuario en la base de datos.
     * Si el usuario tiene ID → actualiza (UPDATE).
     * Si no tiene ID → crea uno nuevo (INSERT).
     * 
     * Nota: aquí es donde en el futuro deberías encriptar la contraseña
     * con BCryptPasswordEncoder antes de guardarla.
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Elimina un usuario por su ID.
     */
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Elimina TODOS los usuarios. Cuidado con este método en producción.
     */
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

}
