package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.UserRepository;

@Service
public interface UserService {
    List<User> findAllUsers();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    void delete(User user);

    

    
    


}
