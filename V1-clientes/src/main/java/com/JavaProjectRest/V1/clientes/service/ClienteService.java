package com.JavaProjectRest.V1.clientes.service;

import com.JavaProjectRest.V1.clientes.model.Cliente;
import com.JavaProjectRest.V1.clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public  Cliente createCliente(Cliente cliente)
    {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> getClienteById(Long id)
    {
        return  clienteRepository.findById(id);
    }

    public List<Cliente> getAllClientes()
    {
        return  clienteRepository.findAll();
    }

    public Cliente updateCliente(Long id, Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        cliente.setName(clienteDetails.getName());
        cliente.setEmail(clienteDetails.getEmail());
        cliente.setPassword(clienteDetails.getPassword());
        return clienteRepository.save(cliente);
    }
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
