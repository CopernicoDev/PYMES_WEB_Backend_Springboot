package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

// Importaciones estándar de Java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// @Service marca esta clase como un componente de Spring (un "bean").
// Spring la detectará automáticamente y la inyectará donde se necesite.
// OJO: esta anotación @Service es de Spring (org.springframework.stereotype.Service),
// NO es nuestra entidad Service del paquete models. Comparten nombre pero son cosas distintas.
import org.springframework.stereotype.Service;

// @Transactional gestiona las transacciones de base de datos automáticamente.
// Si algo falla dentro del método, hace rollback (deshace los cambios).
import org.springframework.transaction.annotation.Transactional;

// Nuestro repositorio que habla directamente con la base de datos
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.ServiceRepository;

/**
 * Implementación concreta de ServiceService.
 * 
 * Esta clase contiene la lógica de negocio para los servicios.
 * Por ahora es simple (delega al repositorio), pero aquí es donde
 * irían reglas de negocio como:
 * - "No se puede crear un servicio con precio negativo"
 * - "Solo el owner puede eliminar su servicio"
 * - "Enviar notificación cuando se crea un servicio"
 */
@Service // Le dice a Spring: "esta clase es un servicio, regístrala como bean"
public class ServiceServiceImpl implements ServiceService {

    // Inyección por constructor (mismo patrón que tu UserServiceImpl).
    // "final" garantiza que no se puede cambiar después de la construcción.
    private final ServiceRepository serviceRepository;

    // Spring ve que ServiceServiceImpl necesita un ServiceRepository
    // y se lo pasa automáticamente por el constructor. Sin @Autowired.
    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Obtiene TODOS los servicios de la base de datos.
     * 
     * @Transactional(readOnly = true) le dice a Hibernate:
     *                         "esta operación solo LEE datos, no modifica nada".
     *                         Esto permite a Hibernate optimizar la consulta (no
     *                         necesita preparar un flush).
     */
    @Override
    @Transactional(readOnly = true)
    public List<com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service> findAll() {
        // CrudRepository.findAll() devuelve Iterable, no List.
        // Por eso lo convertimos manualmente a ArrayList.
        // (Es el mismo truco que usas en UserRepository.findAllUsers())
        List<com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service> services = new ArrayList<>();
        serviceRepository.findAll().forEach(services::add);
        return services;
    }

    /**
     * Busca un servicio por ID.
     * Devuelve Optional.empty() si no existe, en vez de null (evita
     * NullPointerException).
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    /**
     * Guarda un servicio en la base de datos.
     * Si el servicio tiene ID → actualiza (UPDATE).
     * Si no tiene ID → crea uno nuevo (INSERT).
     */
    @Override
    @Transactional // Sin readOnly porque MODIFICA datos
    public com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service save(
            com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service service) {
        return serviceRepository.save(service);
    }

    /**
     * Elimina un servicio por su ID.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }

    /**
     * Elimina TODOS los servicios. Cuidado con este método en producción.
     */
    @Override
    @Transactional
    public void deleteAll() {
        serviceRepository.deleteAll();
    }
}
