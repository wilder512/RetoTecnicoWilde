package com.JavaProjectRest.V1.clientes.repository;

import com.JavaProjectRest.V1.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente,Long> {
}
