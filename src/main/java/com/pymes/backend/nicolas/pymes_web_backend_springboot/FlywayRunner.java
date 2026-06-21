package com.pymes.backend.nicolas.pymes_web_backend_springboot;

// --- Importación de Flyway (herramienta de migraciones de base de datos) ---
import org.flywaydb.core.Flyway;

// --- Importaciones de Spring ---
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// --- Importación estándar de Java para DataSource ---
import javax.sql.DataSource;

/**
 * Ejecutor de migraciones Flyway al arrancar la aplicación.
 * 
 * @Component marca esta clase como un bean de Spring. Se registra automáticamente
 * en el contenedor y Spring la instancia al arrancar.
 * 
 * Implementa ApplicationRunner, lo que significa que su método run()
 * se ejecuta AUTOMÁTICAMENTE después de que Spring Boot haya arrancado
 * completamente (después de configurar DataSource, Hibernate, etc.).
 * 
 * ¿Qué es Flyway?
 * - Es una herramienta de VERSIONADO de base de datos.
 * - Ejecuta scripts SQL (migraciones) en orden para crear/modificar tablas.
 * - Los scripts están en src/main/resources/db/migration/ con nombres como:
 *   V1__crear_tablas.sql, V2__insertar_datos.sql, etc.
 * - Flyway lleva un registro de qué migraciones ya se ejecutaron,
 *   así nunca ejecuta la misma migración dos veces.
 * 
 * Flujo:
 * 1. Spring Boot arranca → configura el DataSource (conexión a BD)
 * 2. Spring detecta que FlywayRunner implementa ApplicationRunner
 * 3. Ejecuta run() → Flyway busca migraciones pendientes y las aplica
 */
@Component
public class FlywayRunner implements ApplicationRunner {

    // @Autowired inyecta el DataSource configurado en application.properties.
    // DataSource es la conexión a la base de datos (URL, usuario, contraseña).
    // Spring lo crea automáticamente basándose en spring.datasource.* del properties.
    @Autowired
    private DataSource dataSource;

    /**
     * Se ejecuta automáticamente al arrancar la aplicación.
     * Configura Flyway con nuestro DataSource y ejecuta las migraciones
     * pendientes desde la carpeta classpath:db/migration.
     * 
     * .configure() → crea un builder de configuración
     * .dataSource(dataSource) → le dice a Flyway qué BD usar
     * .locations("classpath:db/migration") → dónde buscar los scripts SQL
     * .load() → crea la instancia de Flyway con esa configuración
     * .migrate() → ejecuta todas las migraciones pendientes
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load()
                .migrate();
    }
}
