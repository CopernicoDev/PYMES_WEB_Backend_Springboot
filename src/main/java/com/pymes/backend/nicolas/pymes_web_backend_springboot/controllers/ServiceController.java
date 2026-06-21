package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers;

// --- Importaciones de Java estándar ---
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// --- Importaciones de Spring ---
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// --- Importaciones de nuestro proyecto ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.ServiceDTO.ServiceRequestDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.ServiceDTO.ServiceResponseDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.ServiceService;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserService;

// Importamos @Valid para activar las validaciones de nuestro DTO
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar los Servicios IT.
 * 
 * @RestController = @Controller + @ResponseBody
 *                 Significa que cada método devuelve datos JSON directamente
 *                 (no una vista HTML).
 * 
 *                 @RequestMapping("/api/services") define la ruta BASE.
 *                 Todos los endpoints de esta clase empiezan por /api/services.
 * 
 *                 Mismo patrón que UserController ("/api/users") y
 *                 RoleController ("/api/roles").
 */
@RestController
@RequestMapping("/api/services")
public class ServiceController {

    // Dependencias que este controlador necesita para funcionar.
    // serviceService: para operaciones CRUD de servicios
    // userService: para buscar al owner (dueño) cuando se crea un servicio
    private final ServiceService serviceService;
    private final UserService userService;

    /**
     * Inyección por constructor.
     * Spring detecta que necesitamos ServiceService y UserService,
     * busca sus implementaciones (ServiceServiceImpl y UserServiceImpl)
     * y las pasa aquí automáticamente.
     * 
     * Es el mismo patrón que tu UserController(UserService, RoleService).
     */
    public ServiceController(ServiceService serviceService, UserService userService) {
        this.serviceService = serviceService;
        this.userService = userService;
    }

    // ==========================================
    // GET /api/services → Lista todos los servicios
    // ==========================================
    /**
     * Obtiene todos los servicios de la base de datos.
     * 
     * Flujo:
     * 1. serviceService.findAll() → nos da List<Service> (entidades)
     * 2. .stream() → convertimos la lista en un flujo para transformarla
     * 3. .map(ServiceResponseDto::fromEntity) → cada entidad se convierte a DTO
     * 4. .collect(Collectors.toList()) → volvemos a agrupar todo en una lista
     * 
     * ¿Por qué devolvemos DTOs y no entidades?
     * - Seguridad: no exponemos campos internos (ej. la contraseña del owner)
     * - Control: elegimos exactamente qué datos ve el frontend
     */
    @GetMapping
    public List<ServiceResponseDto> getAllServices() {
        return serviceService.findAll()
                .stream()
                .map(ServiceResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // ==========================================
    // GET /api/services/{id} → Obtiene un servicio por ID
    // ==========================================
    /**
     * Busca un servicio específico por su ID.
     * 
     * @PathVariable Long id → extrae el {id} de la URL.
     *               Ejemplo: GET /api/services/5 → id = 5
     * 
     *               ResponseEntity nos permite controlar el código HTTP de
     *               respuesta:
     *               - 200 OK → si el servicio existe
     *               - 404 Not Found → si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> view(@PathVariable Long id) {
        // findById devuelve Optional: puede tener valor o estar vacío
        Optional<com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service> serviceOptional = serviceService
                .findById(id);

        if (serviceOptional.isPresent()) {
            // Si existe: lo convertimos a DTO y devolvemos con 200 OK
            return ResponseEntity.ok(ServiceResponseDto.fromEntity(serviceOptional.get()));
        }
        // Si no existe: devolvemos 404 Not Found (sin cuerpo)
        return ResponseEntity.notFound().build();
    }

    // ==========================================
    // POST /api/services → Crea un servicio nuevo
    // ==========================================
    /**
     * Crea un nuevo servicio IT.
     * 
     * @Valid activa las validaciones del DTO (@NotBlank, @NotNull, @Positive).
     *        Si alguna falla, Spring devuelve automáticamente un error 400 Bad
     *        Request.
     * 
     * @RequestBody le dice a Spring: "el cuerpo JSON de la petición conviértelo
     *              a un objeto ServiceRequestDto".
     * 
     *              Flujo completo:
     *              1. Frontend envía JSON → Spring lo convierte a ServiceRequestDto
     *              2. @Valid verifica que los campos cumplan las reglas
     *              3. Convertimos el DTO a entidad Service (modelo de BD)
     *              4. Buscamos al owner (usuario dueño) en la BD por su ID
     *              5. Guardamos el servicio en la BD
     *              6. Convertimos la entidad guardada a DTO y respondemos con 201
     *              Created
     */
    @PostMapping
    public ResponseEntity<ServiceResponseDto> save(@Valid @RequestBody ServiceRequestDto serviceDto) {

        // PASO 1: Creamos una entidad Service vacía y le asignamos los datos del DTO
        com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service service = new com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service();
        service.setServicename(serviceDto.servicename());
        service.setDescription(serviceDto.description());
        service.setPrice(serviceDto.price());
        service.setCategory(serviceDto.category());
        service.setDuration(serviceDto.duration());
        service.setActive(true); // Un servicio nuevo siempre empieza como activo

        // PASO 2: Buscamos al dueño (owner) del servicio en la base de datos.
        // El frontend nos envía solo el ID del owner (ownerId), no el objeto completo.
        // Necesitamos buscar el User real en la BD para establecer la relación.
        if (serviceDto.ownerId() != null) {
            Optional<User> ownerOpt = userService.findById(serviceDto.ownerId());
            if (ownerOpt.isPresent()) {
                // Si el usuario existe, lo asignamos como dueño del servicio
                service.setOwner(ownerOpt.get());
            }
            // Si el ownerId no existe en la BD, el servicio se crea sin dueño.
            // (Podrías lanzar un error aquí si quieres que sea obligatorio)
        }

        // PASO 3: Guardamos en la base de datos.
        // .save() de JPA hace INSERT si es nuevo o UPDATE si ya tiene ID.
        com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service savedService = serviceService
                .save(service);

        // PASO 4: Devolvemos el servicio guardado como DTO con código 201 CREATED.
        // 201 es el código HTTP estándar para "recurso creado exitosamente".
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ServiceResponseDto.fromEntity(savedService));
    }

    // ==========================================
    // DELETE /api/services/{id} → Elimina un servicio por ID
    // ==========================================
    /**
     * Elimina un servicio específico.
     * 
     * Antes de eliminar, verificamos que exista.
     * Si existe: lo borramos y devolvemos los datos del servicio eliminado (200
     * OK).
     * Si no existe: devolvemos 404 Not Found.
     * 
     * Devolver el servicio eliminado es útil para que el frontend pueda mostrar
     * un mensaje como "Servicio 'Instalación de Firewall' eliminado correctamente".
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> deleteById(@PathVariable Long id) {
        Optional<com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service> serviceOptional = serviceService
                .findById(id);

        if (serviceOptional.isPresent()) {
            // Primero borramos, luego devolvemos el DTO del servicio que acabamos de borrar
            serviceService.deleteById(id);
            return ResponseEntity.ok(ServiceResponseDto.fromEntity(serviceOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ==========================================
    // DELETE /api/services → Elimina todos los servicios
    // ==========================================
    /**
     * Elimina TODOS los servicios de la base de datos.
     * Devuelve 204 No Content (operación exitosa, sin cuerpo en la respuesta).
     * 
     * ⚠️ Este endpoint es peligroso en producción.
     * Más adelante deberías protegerlo con Spring Security para que
     * solo un ADMIN pueda ejecutarlo.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        serviceService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
