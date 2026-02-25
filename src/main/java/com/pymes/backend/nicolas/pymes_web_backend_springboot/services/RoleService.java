package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

import java.util.List;
import java.util.Optional;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;

public interface RoleService {

    Optional<Role> findByRolname(String rolname);

    boolean existsByRolname(String rolname);

    List<Role> findAll();

    List<Role> findByRolnameIn(List<String> rolnames);

    Optional<Role> findById(Long id);

    Role save(Role role);

    void deleteById(Long id);

    void delete(Role role);

    void deleteAll();

}
