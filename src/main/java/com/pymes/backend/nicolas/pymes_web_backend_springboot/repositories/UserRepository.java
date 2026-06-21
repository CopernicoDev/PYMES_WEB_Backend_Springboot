package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

// --- Importaciones de Spring Data ---
// JpaRepository es la evolución de CrudRepository. Incluye TODO lo que tiene
// CrudRepository MÁS estas ventajas:
// - findAll() devuelve List directamente (no Iterable)
// - findAll(Pageable) para paginación automática
// - findAll(Sort) para ordenación
// - flush(), saveAndFlush() para control del EntityManager
import org.springframework.data.jpa.repository.JpaRepository;

// @Query nos permite escribir consultas JPQL personalizadas.
// JPQL es como SQL pero opera sobre entidades Java, no sobre tablas SQL.
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Pageable y Page son las clases de Spring para paginación.
// Pageable: contiene la info de la página solicitada (número, tamaño, orden).
// Page: contiene los resultados + metadatos (total de páginas, total de elementos, etc.)
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

/**
 * Repositorio para acceder a la tabla "users" en la base de datos.
 * 
 * CAMBIO: Migramos de CrudRepository a JpaRepository.
 * 
 * ¿Por qué JpaRepository en vez de CrudRepository?
 * - JpaRepository HEREDA de CrudRepository (tiene todo lo que tenía antes)
 * - ADEMÁS hereda de PagingAndSortingRepository (paginación y ordenación)
 * - Y añade métodos propios como flush() y saveAndFlush()
 * - findAll() ya devuelve List<User> directamente (no necesitamos el truco
 * del método findAllUsers() que teníamos antes)
 * 
 * Jerarquía: JpaRepository → PagingAndSortingRepository → CrudRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * CONSULTA OPTIMIZADA: Trae todos los usuarios CON sus roles en UNA SOLA
	 * consulta.
	 * 
	 * ¿Qué es el problema N+1?
	 * Sin JOIN FETCH, Hibernate hace:
	 * 1 consulta: SELECT * FROM users (trae 100 usuarios)
	 * 100 consultas: SELECT * FROM user_roles WHERE user_id = ? (una por cada
	 * usuario)
	 * Total: 101 consultas para una sola petición GET.
	 * 
	 * Con JOIN FETCH:
	 * 1 sola consulta: SELECT u.*, r.* FROM users u
	 * LEFT JOIN user_roles ur ON u.id = ur.user_id
	 * LEFT JOIN roles r ON ur.role_id = r.id
	 * Total: 1 consulta. Muchísimo más rápido.
	 * 
	 * "LEFT JOIN FETCH u.roles" le dice a Hibernate:
	 * "Cuando traigas los usuarios, trae TAMBIÉN sus roles en la misma consulta".
	 * LEFT JOIN asegura que traiga usuarios incluso si no tienen roles asignados.
	 * 
	 * "SELECT DISTINCT u" evita usuarios duplicados cuando un usuario tiene
	 * múltiples roles (el JOIN genera una fila por cada combinación user-role).
	 */
	@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
	List<User> findAllWithRoles();

	/**
	 * VERSIÓN PAGINADA: Trae usuarios de forma paginada.
	 * 
	 * ¿Por qué paginación?
	 * Si tienes 10.000 usuarios, no tiene sentido traerlos todos de golpe.
	 * Con paginación, traes solo los que necesitas (ej: 20 por página).
	 * 
	 * Spring detecta el parámetro Pageable automáticamente y genera:
	 * SELECT * FROM users LIMIT ? OFFSET ?
	 * 
	 * El frontend puede pedir:
	 * GET /api/users?page=0&size=20 → primera página, 20 resultados
	 * GET /api/users?page=1&size=20 → segunda página, 20 resultados
	 * GET /api/users?page=0&size=20&sort=username,asc → ordenado por nombre
	 * 
	 * Page<User> devuelve no solo los datos, sino también metadatos:
	 * {
	 * "content": [...], // los usuarios de esta página
	 * "totalElements": 150, // total de usuarios en la BD
	 * "totalPages": 8, // total de páginas
	 * "number": 0, // página actual
	 * "size": 20 // tamaño de página
	 * }
	 * 
	 * NOTA: No usamos JOIN FETCH aquí porque JPA no permite JOIN FETCH + Pageable
	 * en la misma query (genera un error). Para la paginación, Hibernate hará
	 * las queries extra, pero eso es aceptable porque solo carga una página (20
	 * items).
	 */
	Page<User> findAll(Pageable pageable);
}
