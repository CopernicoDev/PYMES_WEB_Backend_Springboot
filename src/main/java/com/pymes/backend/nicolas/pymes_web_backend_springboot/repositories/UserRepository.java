package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

// --- Importaciones estándar de Java ---
import java.util.ArrayList;
import java.util.List;

// --- Importación de Spring Data ---
import org.springframework.data.repository.CrudRepository;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

/**
 * Repositorio para acceder a la tabla "users" en la base de datos.
 * 
 * Extiende CrudRepository<User, Long> donde:
 * - User: la entidad JPA que gestiona este repositorio.
 * - Long: el tipo de dato de la clave primaria (id) de User.
 * 
 * CrudRepository nos da GRATIS (sin escribir código) estos métodos:
 * - save(User) → INSERT o UPDATE
 * - findById(Long) → SELECT WHERE id = ?
 * - findAll() → SELECT * (devuelve Iterable, no List)
 * - deleteById(Long) → DELETE WHERE id = ?
 * - deleteAll() → DELETE FROM users
 * - count() → SELECT COUNT(*)
 * - existsById(Long) → comprueba si existe
 * 
 * Spring crea la implementación automáticamente en tiempo de ejecución.
 * No necesitas escribir SQL ni crear una clase que implemente esta interfaz.
 * 
 * Es el mismo patrón que ServiceRepository y RoleRepository.
 */
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Método personalizado que convierte Iterable a List.
	 * 
	 * CrudRepository.findAll() devuelve Iterable<User>, pero en los Controllers
	 * y Services trabajamos con List<User> (más cómodo para transformar con streams).
	 * 
	 * Este método default (implementado directamente en la interfaz):
	 * 1. Crea una lista vacía
	 * 2. Itera sobre todos los usuarios del findAll() original
	 * 3. Los añade uno a uno a la lista con forEach + referencia de método
	 * 
	 * Es un truco necesario porque CrudRepository no devuelve List directamente
	 * (a diferencia de JpaRepository que sí lo hace).
	 */
	default List<User> findAllUsers() {
		List<User> users = new ArrayList<>();
		findAll().forEach(users::add);
		return users;
	}

}
