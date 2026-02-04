package com.pymes.backend.nicolas.pymes_web_backend_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.UserRepository;

@SpringBootApplication
public class PymesWebBackendSpringbootApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(PymesWebBackendSpringbootApplication.class, args);


	}

}
