package com.cllaque.personams.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cllaque.personams.Domain.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, String>{
    
}
