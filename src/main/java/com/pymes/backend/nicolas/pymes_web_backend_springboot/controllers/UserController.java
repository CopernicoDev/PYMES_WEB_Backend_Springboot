package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers;

// --- Importaciones de Java estándar ---
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// --- Importaciones de Spring ---
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// --- Importaciones de nuestro proyecto ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.UserDTO.UserRequestDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.UserDTO.UserResponseDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserService;

// Importamos @Valid para activar las validaciones de nuestro DTO
import jakarta.validation.Valid;

// --- Importaciones de anotaciones de Spring para endpoints ---
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador REST para gestionar los Usuarios.
 * 
 * @RestController = @Controller + @ResponseBody
 *                 Significa que cada método devuelve datos JSON directamente
 *                 (no una vista HTML).
 * 
 *                 @RequestMapping("/api/users") define la ruta BASE.
 *                 Todos los endpoints de esta clase empiezan por /api/users.
 * 
 *                 Mismo patrón que ServiceController ("/api/services") y
 *                 RoleController ("/api/roles").
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Dependencias que este controlador necesita para funcionar.
    // userService: para operaciones CRUD de usuarios
    // roleService: para buscar roles cuando se crea un usuario con roles asignados
    private final UserService userService;
    private final RoleService roleService;

    /**
     * Inyección por constructor.
     * Spring detecta que necesitamos UserService y RoleService,
     * busca sus implementaciones (UserServiceImpl y RoleServiceImpl)
     * y las pasa aquí automáticamente.
     * 
     * Es el mismo patrón que tu ServiceController(ServiceService, UserService).
     */
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // ==========================================
    // GET /api/users → Lista todos los usuarios
    // ==========================================
    /**
     * Obtiene todos los usuarios de la base de datos.
     * 
     * Flujo:
     * 1. userService.findAllUsers() → nos da List<User> (entidades)
     * 2. .stream() → convertimos la lista en un flujo para transformarla
     * 3. .map(UserResponseDto::fromEntity) → cada entidad se convierte a DTO
     * 4. .collect(Collectors.toList()) → volvemos a agrupar todo en una lista
     * 
     * ¿Por qué devolvemos DTOs y no entidades?
     * - Seguridad: no exponemos la CONTRASEÑA del usuario en el JSON
     * - Control: elegimos exactamente qué datos ve el frontend
     */
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // ==========================================
    // GET /api/users/{id} → Obtiene un usuario por ID
    // ==========================================
    /**
     * Busca un usuario específico por su ID.
     * 
     * @PathVariable Long id → extrae el {id} de la URL.
     *               Ejemplo: GET /api/users/5 → id = 5
     * 
     *               ResponseEntity nos permite controlar el código HTTP:
     *               - 200 OK → si el usuario existe
     *               - 404 Not Found → si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> view(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            // Si existe: lo convertimos a DTO y devolvemos con 200 OK
            return ResponseEntity.ok(UserResponseDto.fromEntity(userOptional.get()));
        }
        // Si no existe: devolvemos 404 Not Found (sin cuerpo)
        return ResponseEntity.notFound().build();
    }

    // ==========================================
    // POST /api/users → Crea un usuario nuevo
    // ==========================================
    /**
     * Crea un nuevo usuario.
     * 
     * @Valid activa las validaciones del DTO (@NotBlank, @Email, @Size).
     *        Si alguna falla, Spring devuelve automáticamente un error 400 Bad
     *        Request.
     * 
     * @RequestBody le dice a Spring: "el cuerpo JSON de la petición conviértelo
     *              a un objeto UserRequestDto".
     * 
     *              Flujo completo:
     *              1. Frontend envía JSON → Spring lo convierte a UserRequestDto
     *              2. @Valid verifica que los campos cumplan las reglas
     *              3. Convertimos el DTO a entidad User (modelo de BD)
     *              4. Buscamos cada rol por nombre en la BD y lo asignamos
     *              5. Guardamos el usuario en la BD
     *              6. Convertimos la entidad guardada a DTO y respondemos con 201
     *              Created
     */
    @PostMapping()
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto userDto) {
        // PASO 1: Creamos una entidad User vacía y le asignamos los datos del DTO
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());

        // PASO 2: Asignamos los roles al usuario.
        // El frontend envía los NOMBRES de los roles (ej: ["ROLE_ADMIN", "ROLE_USER"]).
        // Necesitamos buscar cada Role real en la BD para establecer la relación.
        if (userDto.roles() != null) {
            for (String rolName : userDto.roles()) {
                // Buscamos el rol por su nombre en la base de datos
                Optional<Role> roleOpt = roleService.findByRolname(rolName);

                // Si el rol existe en la BD, lo añadimos al usuario
                if (roleOpt.isPresent()) {
                    user.addRole(roleOpt.get());
                }
                // Si el rol no existe en la BD, simplemente se ignora.
                // (Podrías lanzar un error aquí si quieres que sea obligatorio)
            }
        }

        // PASO 3: Guardamos la entidad en la base de datos
        User savedUser = userService.save(user);

        // PASO 4: Convertimos la entidad guardada a DTO y respondemos con 201 CREATED.
        // 201 es el código HTTP estándar para "recurso creado exitosamente".
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.fromEntity(savedUser));
    }

    // ==========================================
    // DELETE /api/users/{id} → Elimina un usuario por ID
    // ==========================================
    /**
     * Elimina un usuario específico.
     * 
     * Antes de eliminar, verificamos que exista.
     * Si existe: lo borramos y devolvemos los datos del usuario eliminado (200 OK).
     * Si no existe: devolvemos 404 Not Found.
     * 
     * Devolver el usuario eliminado es útil para que el frontend pueda mostrar
     * un mensaje como "Usuario 'nicolas' eliminado correctamente".
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            // Primero borramos, luego devolvemos el DTO del usuario que acabamos de borrar
            userService.deleteById(id);
            return ResponseEntity.ok(UserResponseDto.fromEntity(userOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ==========================================
    // DELETE /api/users → Elimina todos los usuarios
    // ==========================================
    /**
     * Elimina TODOS los usuarios de la base de datos.
     * Devuelve 204 No Content (operación exitosa, sin cuerpo en la respuesta).
     * 
     * ⚠️ Este endpoint es peligroso en producción.
     * Más adelante deberías protegerlo con Spring Security para que
     * solo un ADMIN pueda ejecutarlo.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}