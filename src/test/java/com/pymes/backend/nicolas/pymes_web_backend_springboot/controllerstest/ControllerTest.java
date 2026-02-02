package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllerstest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers.UserController;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserService;

@WebMvcTest(UserController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockitoBean
    private UserService userService;

    @Test
    void testfindAllUsers() {
        //Given
         // Given - crear datos inline
        User u1 = new User();
        u1.setPersona("Nicolás");
        u1.setId(1L);

        User u2 = new User();
        u2.setPersona("Yaiza");
        u2.setId(2L);
        java.util.List<User> users = List.of(u1,u2);
        when(userService.findAll()).thenReturn(users);

        //When , Then 
        mockmvc.perform(get("/API/users/listar").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(users.size()))
        .andExpect(jsonPath("$[0].persona").value("Nicolás"))
        .andExpect(jsonPath("$[1].persona").value("Yaiza"));

        verify(userService).findAll();
        assertFalse(users.isEmpty());
        assertEquals(2,users.size());
        assertEquals("Nicolás", users.get(0).getPersona());
        
    }
    

}
