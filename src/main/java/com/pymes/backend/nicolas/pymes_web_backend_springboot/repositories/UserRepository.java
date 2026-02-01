package com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

	default List<User> findAllUsers() {
		List<User> users = new ArrayList<>();
		findAll().forEach(users::add);
		return users;
	}


}
