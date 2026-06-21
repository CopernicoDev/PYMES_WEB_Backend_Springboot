package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

// Importamos las clases que necesitamos
import java.util.List;
import java.util.Optional;

// --- Importaciones de Spring Data ---
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Importamos nuestra entidad Service (el modelo de la base de datos)
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service;

/**
 * Interfaz que define el CONTRATO de operaciones disponibles para Service.
 * 
 * ¿Por qué una interfaz y no directamente la clase?
 * - Desacoplamiento: el Controller depende del contrato, no de la
 * implementación.
 * - Testeabilidad: en tests puedes crear un mock que implemente esta interfaz.
 * - Flexibilidad: podrías tener varias implementaciones (ej. una con caché).
 * 
 * Es el mismo patrón que ya usas con UserService y RoleService.
 */
public interface ServiceService {

    // Devuelve todos los servicios de la base de datos
    List<Service> findAll();

    // Busca un servicio por su ID. Devuelve Optional porque puede no existir.
    Optional<Service> findById(Long id);

    // Guarda un servicio nuevo o actualiza uno existente. Devuelve el servicio
    // guardado.
    Service save(Service service);

    // Elimina un servicio por su ID
    void deleteById(Long id);

    // Elimina todos los servicios
    void deleteAll();

    Page<Service> findAll(Pageable pageable);
}
