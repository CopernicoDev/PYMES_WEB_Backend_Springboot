package com.pymes.backend.nicolas.pymes_web_backend_springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.UserRepository;

@SpringBootApplication
public class PymesWebBackendSpringbootApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(PymesWebBackendSpringbootApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		List<User> users = userRepository.findAllUsers();
		users.stream().forEach(user -> System.out.println(user));
	}


}
