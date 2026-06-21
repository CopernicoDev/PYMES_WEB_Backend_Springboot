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

// Importaciones de Spring Data
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// --- Importaciones de nuestro proyecto ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.RoleDTO.RoleResponseDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;

/**
 * Controlador REST para gestionar los Roles de usuario.
 * 
 * @RestController = @Controller + @ResponseBody
 *                 Significa que cada método devuelve datos JSON directamente
 *                 (no una vista HTML).
 * 
 *                 @RequestMapping("/api/roles") define la ruta BASE.
 *                 Todos los endpoints de esta clase empiezan por /api/roles.
 * 
 *                 Mismo patrón que UserController ("/api/users") y
 *                 ServiceController ("/api/services").
 * 
 *                 Nota: A diferencia de UserController y ServiceController,
 *                 este controller
 *                 recibe la entidad Role directamente en el POST (sin DTO de
 *                 entrada).
 *                 Esto es porque Role es muy simple (solo tiene id y rolname).
 *                 En el futuro podrías crear un RoleRequestDto si necesitas
 *                 validaciones.
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    // Dependencia que este controlador necesita para funcionar.
    // roleService: para operaciones CRUD de roles
    private final RoleService roleService;

    /**
     * Inyección por constructor.
     * Spring detecta que necesitamos RoleService,
     * busca su implementación (RoleServiceImpl)
     * y la pasa aquí automáticamente.
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // ==========================================
    // GET /api/roles → Lista todos los roles
    // ==========================================
    /**
     * Obtiene todos los roles de la base de datos.
     * 
     * Flujo:
     * 1. roleService.findAll() → nos da List<Role> (entidades)
     * 2. .stream() → convertimos la lista en un flujo para transformarla
     * 3. .map(RoleResponseDto::fromEntity) → cada entidad se convierte a DTO
     * 4. .collect(Collectors.toList()) → volvemos a agrupar todo en una lista
     */
    @GetMapping
    public Page<RoleResponseDto> getAllRoles(Pageable pageable) {
        return roleService.findAll(pageable)
                .map(RoleResponseDto::fromEntity);
    }

    // ==========================================
    // GET /api/roles/{id} → Obtiene un rol por ID
    // ==========================================
    /**
     * Busca un rol específico por su ID.
     * 
     * @PathVariable Long id → extrae el {id} de la URL.
     *               Ejemplo: GET /api/roles/1 → id = 1
     * 
     *               Respuestas:
     *               - 200 OK → si el rol existe (devuelve el DTO)
     *               - 404 Not Found → si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> view(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.findById(id);
        if (roleOptional.isPresent()) {
            // Si existe: lo convertimos a DTO y devolvemos con 200 OK
            return ResponseEntity.ok(RoleResponseDto.fromEntity(roleOptional.get()));
        } else {
            // Si no existe: devolvemos 404 Not Found (sin cuerpo)
            return ResponseEntity.notFound().build();
        }
    }

    // ==========================================
    // POST /api/roles → Crea un rol nuevo
    // ==========================================
    /**
     * Crea un nuevo rol.
     * 
     * A diferencia de UserController y ServiceController, aquí recibimos
     * directamente la entidad Role (sin DTO de entrada) porque Role es
     * muy simple (solo tiene rolname).
     * 
     * @RequestBody le dice a Spring: "el cuerpo JSON de la petición conviértelo
     *              a un objeto Role".
     * 
     *              Flujo:
     *              1. Frontend envía JSON { "rolname": "ROLE_MODERATOR" }
     *              2. Spring lo convierte a una entidad Role
     *              3. Guardamos en la BD
     *              4. Devolvemos el rol guardado como DTO con 201 CREATED
     */
    @PostMapping
    public ResponseEntity<RoleResponseDto> save(@RequestBody Role role) {
        Role savedRole = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleResponseDto.fromEntity(savedRole));
    }

    // ==========================================
    // DELETE /api/roles/{id} → Elimina un rol por ID
    // ==========================================
    /**
     * Elimina un rol específico.
     * 
     * Antes de eliminar, verificamos que exista.
     * Si existe: lo borramos y devolvemos los datos del rol eliminado (200 OK).
     * Si no existe: devolvemos 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RoleResponseDto> deleteById(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.findById(id);
        if (roleOptional.isPresent()) {
            // Primero borramos, luego devolvemos el DTO del rol que acabamos de borrar
            roleService.deleteById(id);
            return ResponseEntity.ok(RoleResponseDto.fromEntity(roleOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ==========================================
    // DELETE /api/roles → Elimina todos los roles
    // ==========================================
    /**
     * Elimina TODOS los roles de la base de datos.
     * Devuelve 204 No Content (operación exitosa, sin cuerpo en la respuesta).
     * 
     * ⚠️ Este endpoint es peligroso en producción.
     * Más adelante deberías protegerlo con Spring Security para que
     * solo un ADMIN pueda ejecutarlo.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        roleService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
