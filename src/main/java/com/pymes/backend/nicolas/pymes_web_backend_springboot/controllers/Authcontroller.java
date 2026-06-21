package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers;

/**
 * Controlador REST para gestionar la Autenticación.
 * 
 * Este controlador está VACÍO por ahora. Será implementado cuando
 * se integre Spring Security en el proyecto.
 * 
 * Aquí irán los endpoints de autenticación como:
 * - POST /api/auth/login → iniciar sesión (devuelve un token JWT)
 * - POST /api/auth/register → registrar un nuevo usuario
 * - POST /api/auth/refresh → renovar el token JWT
 * - POST /api/auth/logout → cerrar sesión (invalidar el token)
 * 
 * Futuras anotaciones que necesitará:
 * - @RestController → para que devuelva JSON
 * - @RequestMapping("/api/auth") → ruta base para autenticación
 * 
 * Dependerá de:
 * - AuthenticationManager (Spring Security) para validar credenciales
 * - JwtProvider (custom) para generar y validar tokens JWT
 * - UserService para buscar usuarios en la BD
 */
public class Authcontroller {

}
