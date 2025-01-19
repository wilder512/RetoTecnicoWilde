package com.JavaProjectRest.V1.clientes.controller;


import com.JavaProjectRest.V1.clientes.model.Cliente;
import com.JavaProjectRest.V1.clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> createUser(@RequestBody Cliente cliente) {
        logger.info(HttpStatus.CREATED.toString());
        return new ResponseEntity<>(clienteService.createCliente(cliente), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getUserById(@PathVariable Long id) {
        logger.info(HttpStatus.OK.toString());
        return clienteService.getClienteById(id)
                .map(cliente -> new ResponseEntity<>(cliente,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllUsers() {
        logger.info(HttpStatus.OK.toString());
        return new ResponseEntity<>(clienteService.getAllClientes(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateUser(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        try {
            logger.info(HttpStatus.OK.toString());
            return new ResponseEntity<>(clienteService.updateCliente(id, clienteDetails), HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.info(HttpStatus.NO_CONTENT.toString());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
