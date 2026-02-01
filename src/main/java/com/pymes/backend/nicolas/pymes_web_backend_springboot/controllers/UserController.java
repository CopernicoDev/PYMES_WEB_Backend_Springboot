package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

public class UserController {

    @Autowired
    private UserController userController;

    public List<User> getallUsers(){
        return userController.findAllUsers();
    }

}
