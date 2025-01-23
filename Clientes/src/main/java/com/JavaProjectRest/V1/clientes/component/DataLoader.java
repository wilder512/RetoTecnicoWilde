package com.JavaProjectRest.V1.clientes.component;
import com.JavaProjectRest.V1.clientes.model.Cliente;
import com.JavaProjectRest.V1.clientes.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    public DataLoader(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear lista de clientes
        List<Cliente> clientes = List.of(
                new Cliente(null, "Alice", "alice@example.com", "password1", "standard", new BigDecimal("1000.00")),
                new Cliente(null, "Bob", "bob@example.com", "password2", "premium", new BigDecimal("2000.00")),
                new Cliente(null, "Charlie", "charlie@example.com", "password3", "standard", new BigDecimal("1500.00")),
                new Cliente(null, "Diana", "diana@example.com", "password4", "standard", new BigDecimal("500.00")),
                new Cliente(null, "Eve", "eve@example.com", "password5", "premium", new BigDecimal("3000.00")),
                new Cliente(null, "Frank", "frank@example.com", "password6", "standard", new BigDecimal("2500.00")),
                new Cliente(null, "Grace", "grace@example.com", "password7", "premium", new BigDecimal("4000.00")),
                new Cliente(null, "Hank", "hank@example.com", "password8", "standard", new BigDecimal("3500.00")),
                new Cliente(null, "Ivy", "ivy@example.com", "password9", "premium", new BigDecimal("4500.00")),
                new Cliente(null, "Jack", "jack@example.com", "password10", "standard", new BigDecimal("1200.00"))
        );

        // Guardar clientes en la base de datos
        clienteRepository.saveAll(clientes);

        System.out.println("Datos de clientes cargados correctamente.");
    }
}
