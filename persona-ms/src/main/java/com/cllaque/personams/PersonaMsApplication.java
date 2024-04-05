package com.cllaque.personams;

import java.util.NoSuchElementException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cllaque.personams.Domain.Cliente;
import com.cllaque.personams.Domain.Persona;
import com.cllaque.personams.Repository.ClienteRepository;

@SpringBootApplication
public class PersonaMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonaMsApplication.class, args);
	}
}
