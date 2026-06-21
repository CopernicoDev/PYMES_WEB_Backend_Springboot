package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

// --- Importaciones de Spring Data ---
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service;

/**
 * Repositorio para acceder a la tabla de servicios en la base de datos.
 * 
 * CAMBIO: Migramos de CrudRepository a JpaRepository.
 * Ahora soporta paginación y tiene findAll() que devuelve List directamente.
 */
public interface ServiceRepository extends JpaRepository<Service, Long> {

    /**
     * CONSULTA OPTIMIZADA: Trae todos los servicios CON su owner en UNA SOLA
     * consulta.
     * 
     * Sin JOIN FETCH:
     * 1 consulta: SELECT * FROM services (trae 50 servicios)
     * 50 consultas: SELECT * FROM users WHERE id = ? (una por cada owner)
     * Total: 51 consultas.
     * 
     * Con JOIN FETCH:
     * 1 sola consulta: SELECT s.*, u.* FROM services s
     * LEFT JOIN users u ON s.owner_id = u.id
     * Total: 1 consulta.
     * 
     * LEFT JOIN FETCH asegura que también traiga servicios que no tienen owner.
     */
    @Query("SELECT s FROM Service s LEFT JOIN FETCH s.owner")
    List<Service> findAllWithOwner();

    /**
     * VERSIÓN PAGINADA: Trae servicios de forma paginada.
     * 
     * El frontend puede pedir:
     * GET /api/services?page=0&size=10 → primera página, 10 resultados
     * GET /api/services?page=0&size=10&sort=price,desc → ordenado por precio
     * descendente
     */
    Page<Service> findAll(Pageable pageable);
}
