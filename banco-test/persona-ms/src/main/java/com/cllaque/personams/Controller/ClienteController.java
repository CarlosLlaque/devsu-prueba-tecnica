package com.cllaque.personams.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.personams.DTO.CrearClienteReq;
import com.cllaque.personams.Domain.Cliente;
import com.cllaque.personams.Service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping("/{dni}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente obtenerCliente(@PathVariable String dni){
        return this.clienteService.obtenerCliente(dni);
    }

    @PostMapping("/crear")
    public Cliente crearCliente(@RequestBody CrearClienteReq req){
        return this.clienteService.crearCliente(req);
    }

    @PutMapping("/actualizar")
    @ResponseStatus(HttpStatus.OK)
    public Cliente actualizarCliente(@RequestBody CrearClienteReq req){
        return this.clienteService.actualizarCliente(req);
    }

    @DeleteMapping("/{dni}")
    public void eliminarCliente(@PathVariable String dni){
        this.clienteService.eliminarCliente(dni);
    }
}
