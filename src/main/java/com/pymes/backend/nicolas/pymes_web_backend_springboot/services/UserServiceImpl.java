package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

public class UserServiceImpl implements UserService {

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllUsers'");
    }

    @Override
    public Optional<User> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public User save(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void delete(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


}
