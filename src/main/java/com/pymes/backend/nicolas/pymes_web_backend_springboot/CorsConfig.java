package com.pymes.backend.nicolas.pymes_web_backend_springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de CORS (Cross-Origin Resource Sharing).
 * 
 * Permite que nuestro frontend en Angular (localhost:4200) pueda
 * comunicarse con este backend sin que el navegador bloquee las peticiones
 * por motivos de seguridad.
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Aplica a todos los endpoints de nuestra API (/api/users, /api/roles, etc.)
                registry.addMapping("/api/**")
                        // Permite que solo Angular pueda hacer peticiones a estos endpoints
                        // En el futuro, si subes el frontend a internet, aquí pondrías el dominio real
                        // (ej: "https://itsup.com")
                        .allowedOrigins("http://localhost:4200")
                        // Permite los métodos HTTP que vamos a usar
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Permite que el frontend envíe headers como Authorization (para el JWT que
                        // haremos luego)
                        .allowedHeaders("*")
                        // Permite enviar cookies/credenciales en las peticiones
                        .allowCredentials(true)
                        // Tiempo que el navegador cacheará esta configuración (1 hora)
                        .maxAge(3600);
            }
        };
    }
}
