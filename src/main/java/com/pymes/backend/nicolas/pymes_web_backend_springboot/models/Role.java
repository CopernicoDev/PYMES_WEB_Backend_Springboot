package com.pymes.backend.nicolas.pymes_web_backend_springboot.models;

// --- Importaciones estándar de Java ---
import java.util.HashSet;
import java.util.Set;

// --- Importación de Jackson (serialización JSON) ---
import com.fasterxml.jackson.annotation.JsonIgnore;

// --- Importaciones de JPA (Java Persistence API) ---
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un ROL de usuario en la base de datos.
 * 
 * @Entity mapea esta clase Java a una tabla en la BD.
 * @Table(name = "roles") especifica que la tabla se llama "roles".
 * 
 * Los roles definen los PERMISOS de un usuario en el sistema.
 * Ejemplos de roles: "ROLE_ADMIN", "ROLE_USER", "ROLE_MODERATOR".
 * 
 * Un rol puede pertenecer a MUCHOS usuarios → relación ManyToMany inversa
 * (el lado "dueño" de la relación está en User).
 */
@Entity
@Table(name = "roles")
public class Role {

    // --- Clave primaria ---
    // @Id + @GeneratedValue: el ID se genera automáticamente con auto-incremento.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del rol (ej: "ROLE_ADMIN", "ROLE_USER").
    // Spring Security espera por convención que los roles empiecen con "ROLE_".
    private String rolname;

    /**
     * Relación ManyToMany INVERSA con User.
     * 
     * mappedBy = "roles" → indica que la relación ya está definida en User.roles.
     *   Esto significa que Role NO es dueño de la relación.
     *   La tabla intermedia "user_roles" se configura en User, no aquí.
     * 
     * fetch = FetchType.LAZY: los usuarios asociados a este rol NO se cargan
     *         automáticamente. Solo se cargan cuando accedes a getUsers().
     * 
     * @JsonIgnore: EVITA que Jackson (el serializador JSON) incluya los usuarios
     *              al serializar un Role. Sin esto, tendrías un bucle infinito:
     *              Role → Users → Roles → Users → ... (StackOverflowError).
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    // --- Getters y Setters ---

    /** Devuelve los usuarios que tienen este rol. */
    public Set<User> getUsers() {
        return users;
    }

    /** Reemplaza el conjunto de usuarios que tienen este rol. */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    /** Devuelve el nombre del rol (ej: "ROLE_ADMIN"). */
    public String getRolname() {
        return rolname;
    }

    /** Establece el nombre del rol. */
    public void setRolname(String rolname) {
        this.rolname = rolname;
    }

}
