package com.pymes.backend.nicolas.pymes_web_backend_springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByRolname(String rolname) {
        return repository.findByRolname(rolname);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRolname(String rolname) {
        return repository.existsByRolname(rolname);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return (List<Role>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findByRolnameIn(List<String> rolnames) {
        return repository.findByRolnameIn(rolnames);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Role role) {
        repository.delete(role);
    }

    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }
}
