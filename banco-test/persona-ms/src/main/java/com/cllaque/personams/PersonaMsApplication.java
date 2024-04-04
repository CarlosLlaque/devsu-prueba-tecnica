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

    @Bean
    CommandLineRunner runner(ClienteRepository repository) {
        return args -> {
            var persona = new Cliente();
            persona.setDni("12312312");
            persona.setEdad(22);
            persona.setGenero("M");
            persona.setNombre("Carlos");
            persona.setTelefono("999999999");
            persona.setDireccion("AV. algun lugar");

            persona.setClienteId("asdfsafasf");

            repository.save(persona);

            Persona saved = repository.findById(persona.getDni()).orElseThrow(NoSuchElementException::new);
        };
    }
}
