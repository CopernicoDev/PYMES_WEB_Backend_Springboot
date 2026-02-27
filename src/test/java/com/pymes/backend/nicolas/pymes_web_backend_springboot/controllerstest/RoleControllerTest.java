package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllerstest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers.RoleController;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoleService roleService;

}
