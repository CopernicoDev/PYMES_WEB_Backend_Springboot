package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
