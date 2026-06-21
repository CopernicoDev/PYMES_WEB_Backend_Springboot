package com.pymes.backend.nicolas.pymes_web_backend_springboot;

// --- Importaciones de Spring Boot ---
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * 
 * @SpringBootApplication es una anotación "3 en 1" que combina:
 * - @Configuration: permite definir beans de configuración en esta clase.
 * - @EnableAutoConfiguration: Spring configura automáticamente los componentes
 *   según las dependencias del pom.xml (ej: si tienes JPA, configura Hibernate).
 * - @ComponentScan: escanea este paquete y subpaquetes buscando @Component,
 *   @Service, @Repository, @Controller, etc. para registrarlos como beans.
 * 
 * Esta clase es el PUNTO DE ENTRADA de toda la aplicación.
 * Al ejecutarla, Spring Boot arranca el servidor embebido (Tomcat),
 * configura la base de datos, registra todos los beans y deja la API lista.
 */
@SpringBootApplication
public class PymesWebBackendSpringbootApplication {

	/**
	 * Método main: punto de entrada de Java.
	 * SpringApplication.run() arranca todo el contexto de Spring:
	 * 1. Crea el ApplicationContext (contenedor de beans)
	 * 2. Escanea y registra todos los componentes (@Service, @Controller, etc.)
	 * 3. Configura la conexión a base de datos (DataSource, Hibernate)
	 * 4. Arranca el servidor Tomcat embebido en el puerto configurado
	 * 5. Ejecuta los ApplicationRunner (como FlywayRunner)
	 */
	public static void main(String[] args) {
		SpringApplication.run(PymesWebBackendSpringbootApplication.class, args);
	}

}
