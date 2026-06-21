package com.pymes.backend.nicolas.pymes_web_backend_springboot;

// --- Importaciones de Spring ---
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

/**
 * Clase de configuración para Spring Data REST.
 * 
 * @Configuration le dice a Spring: "esta clase contiene configuración adicional".
 * Spring la procesa al arrancar y aplica las personalizaciones que definas aquí.
 * 
 * Implementa RepositoryRestConfigurer, que permite personalizar cómo
 * Spring Data REST expone los repositorios como endpoints automáticos.
 * 
 * ¿Qué es Spring Data REST?
 * - Genera endpoints REST automáticamente a partir de tus repositorios.
 * - Por ejemplo, si tienes UserRepository, crea automáticamente
 *   GET/POST/PUT/DELETE en /users sin necesidad de un Controller.
 * 
 * Actualmente esta clase está VACÍA, pero aquí podrías:
 * - Exponer los IDs de las entidades en las respuestas JSON
 * - Configurar CORS para Spring Data REST
 * - Cambiar la ruta base de los endpoints automáticos
 * - Restringir qué repositorios se exponen
 * 
 * Ejemplo de uso futuro:
 *   @Override
 *   public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
 *       config.exposeIdsFor(User.class, Role.class, Service.class);
 *   }
 */
@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

}
