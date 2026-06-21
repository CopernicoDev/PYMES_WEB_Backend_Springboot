package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

// --- Importación de Spring Data ---
import org.springframework.data.repository.CrudRepository;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service;

/**
 * Repositorio para acceder a la tabla de servicios en la base de datos.
 * 
 * Extiende CrudRepository<Service, Long> donde:
 * - Service: la entidad JPA que gestiona este repositorio.
 * - Long: el tipo de dato de la clave primaria (id) de Service.
 * 
 * CrudRepository nos da GRATIS estos métodos (sin escribir código):
 * - save(Service) → INSERT o UPDATE
 * - findById(Long) → SELECT WHERE id = ?
 * - findAll() → SELECT * (devuelve Iterable)
 * - deleteById(Long) → DELETE WHERE id = ?
 * - deleteAll() → DELETE FROM service
 * - count() → SELECT COUNT(*)
 * - existsById(Long) → comprueba si existe
 * 
 * Este repositorio está VACÍO porque los métodos heredados de CrudRepository
 * son suficientes. Más adelante podrías añadir consultas personalizadas como:
 * - List<Service> findByCategory(String category);
 * - List<Service> findByActiveTrue();
 * - List<Service> findByOwnerId(Long ownerId);
 * 
 * Spring genera la implementación automáticamente a partir del nombre del método
 * (Query Methods). No necesitas escribir SQL.
 */
public interface ServiceRepository extends CrudRepository<Service , Long> {

}
