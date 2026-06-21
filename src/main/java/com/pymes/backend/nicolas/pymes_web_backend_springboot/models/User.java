package com.pymes.backend.nicolas.pymes_web_backend_springboot.models;

// --- Importaciones estándar de Java ---
import java.util.HashSet;
import java.util.Set;

// --- Importaciones de JPA (Java Persistence API) ---
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un USUARIO en la base de datos.
 * 
 * @Entity le dice a Hibernate: "esta clase Java se mapea a una tabla de la BD".
 *         Cada instancia de User corresponde a una FILA en la tabla.
 * 
 * @Table(name = "users") especifica el nombre de la tabla en la BD.
 *        Sin esta anotación, Hibernate usaría el nombre de la clase ("User"),
 *        pero "user" es una palabra reservada en algunos motores SQL,
 *        así que usamos "users" para evitar conflictos.
 * 
 * Un usuario puede tener VARIOS roles (ADMIN, USER, etc.) →
 * relación ManyToMany con la entidad Role a través de la tabla intermedia "user_roles".
 */
@Entity
@Table(name = "users")
public class User {

    // --- Clave primaria ---
    // @Id marca este campo como la PRIMARY KEY de la tabla.
    // @GeneratedValue(strategy = IDENTITY) significa que la BD genera el ID
    // automáticamente con auto-incremento (1, 2, 3...).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos básicos del usuario. Hibernate los mapea automáticamente
    // a columnas con el mismo nombre en la tabla "users".
    private String username;
    private String email;
    private String password;

    /**
     * Relación Muchos-a-Muchos con Role.
     * 
     * Un usuario puede tener MUCHOS roles y un rol puede pertenecer a MUCHOS
     * usuarios.
     * 
     * @ManyToMany define la relación bidireccional.
     * 
     * fetch = FetchType.LAZY: los roles NO se cargan automáticamente al consultar
     *         un usuario. Solo se cargan cuando accedes a getRoles().
     *         Esto mejora el rendimiento (evita cargar datos innecesarios).
     * 
     * @JoinTable configura la TABLA INTERMEDIA "user_roles":
     *           - joinColumns: columna que referencia a ESTE lado (user_id)
     *           - inverseJoinColumns: columna que referencia al OTRO lado (role_id)
     * 
     * Ejemplo de la tabla intermedia:
     *   user_roles: | user_id | role_id |
     *               |    1    |    1    |  → Usuario 1 tiene Rol 1
     *               |    1    |    2    |  → Usuario 1 también tiene Rol 2
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // --- Métodos para gestionar roles ---

    /** Devuelve el conjunto de roles del usuario. */
    public Set<Role> getRoles() {
        return roles;
    }

    /** Reemplaza todos los roles del usuario por un nuevo conjunto. */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /** Añade un rol individual al usuario (ej: darle permisos de ADMIN). */
    public void addRole(Role role) {
        this.roles.add(role);
    }

    /** Quita un rol individual al usuario (ej: revocar permisos de ADMIN). */
    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    // --- Constructores ---

    /**
     * Constructor vacío. OBLIGATORIO para JPA/Hibernate.
     * Hibernate necesita crear instancias vacías de User y luego
     * rellenar los campos con los datos de la BD usando los setters.
     */
    public User() {
    }

    /**
     * Constructor con todos los campos principales.
     * Útil para crear usuarios en tests o cuando necesitas una instancia completa.
     */
    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // --- Getters y Setters ---
    // JPA/Hibernate los usa internamente para leer y escribir los campos
    // al hacer operaciones con la base de datos.

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Representación en texto del usuario para debugging.
     * No incluye la contraseña por seguridad.
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + "]";
    }

}
