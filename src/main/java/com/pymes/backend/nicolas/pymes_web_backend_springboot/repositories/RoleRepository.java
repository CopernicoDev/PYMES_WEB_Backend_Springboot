package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRolname(String rolname);

    boolean existsByRolname(String rolname);

    List<Role> findAll();

    List<Role> findByRolnameIn(List<String> rolnames);

    Optional<Role> findById(Long id);

    void deleteById(Long id);

    void deleteAll();

}
