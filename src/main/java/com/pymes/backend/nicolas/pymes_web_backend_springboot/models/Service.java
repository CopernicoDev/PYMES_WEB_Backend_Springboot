package com.pymes.backend.nicolas.pymes_web_backend_springboot.models;

// --- Importación estándar de Java ---
import java.math.BigDecimal;

// --- Importaciones de JPA (Java Persistence API) ---
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Entidad JPA que representa un SERVICIO IT en la base de datos.
 * 
 * @Entity mapea esta clase Java a una tabla en la BD.
 *         Al no especificar @Table(name = "..."), Hibernate usa el nombre
 *         de la clase en minúsculas como nombre de tabla → "service".
 * 
 * Un servicio es un producto IT que una PYME puede ofrecer a sus clientes.
 * Ejemplos: "Instalación de Firewall", "Auditoría de Seguridad",
 * "Mantenimiento de Servidores", etc.
 * 
 * Cada servicio tiene un DUEÑO (owner) → relación ManyToOne con User.
 * Muchos servicios pueden pertenecer a un mismo usuario.
 */
@Entity
public class Service {

    // --- Clave primaria con auto-incremento ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del servicio (ej: "Instalación de Firewall")
    private String servicename;

    // Descripción detallada del servicio
    private String description;

    // Precio del servicio. Usamos BigDecimal en vez de Double porque
    // BigDecimal es PRECISO para operaciones monetarias.
    // Double puede dar errores de redondeo (ej: 0.1 + 0.2 = 0.30000000000000004).
    private BigDecimal price;

    // Categoría del servicio (ej: "Seguridad", "Redes", "Cloud")
    private String category;

    // Duración estimada del servicio en minutos
    private Integer duration;

    // Indica si el servicio está activo (visible) o desactivado.
    // Usamos Boolean (objeto) en vez de boolean (primitivo) para permitir null.
    private Boolean active;

    /**
     * Relación Muchos-a-Uno con User (el dueño del servicio).
     * 
     * @ManyToOne: muchos servicios pueden pertenecer a UN usuario.
     *             Es la relación inversa de lo que sería OneToMany en User.
     * 
     * fetch = FetchType.LAZY: el usuario dueño NO se carga automáticamente
     *         al consultar el servicio. Solo se carga cuando accedes a getOwner().
     *         Esto es clave para el rendimiento: si listas 100 servicios,
     *         no quieres cargar 100 usuarios automáticamente.
     * 
     * @JoinColumn(name = "owner_id"): especifica que en la tabla "service"
     *             hay una columna "owner_id" que es la FOREIGN KEY hacia la
     *             tabla "users".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    // --- Getters y Setters del owner ---

    /** Devuelve el usuario dueño de este servicio. */
    public User getOwner() { return owner; }

    /** Asigna un usuario como dueño de este servicio. */
    public void setOwner(User owner) { this.owner = owner; }

    // --- Getters y Setters de los campos del servicio ---
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getServicename() {
        return servicename;
    }
    public void setServicename(String servicename) {
        this.servicename = servicename;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

}
