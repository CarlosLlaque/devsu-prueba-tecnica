package com.cllaque.compositems.ut;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import com.cllaque.compositems.dto.CrearClienteReq;
import com.cllaque.compositems.service.ClienteService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class ClienteMessagingTest {
    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private ClienteService clienteService;

    @Test
    public void crearCliente(){
        var req = new CrearClienteReq();
        req.setDni("12312311");
        req.setEdad(12);
        req.setEstado(true);
        req.setContrasena("123");
        req.setDireccion("MyDirec");
        req.setGenero("M");
        req.setNombre("AAAAA");
        req.setTelefono("88881111");

        this.clienteService.crearCliente(req);

        var messagePayload = new String(this.outputDestination.receive(0, "clientes").getPayload());

        System.out.println(messagePayload);
    }
}
