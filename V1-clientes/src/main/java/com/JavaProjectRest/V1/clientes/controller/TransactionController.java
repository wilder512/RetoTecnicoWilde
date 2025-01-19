package com.JavaProjectRest.V1.clientes.controller;

import com.JavaProjectRest.V1.clientes.model.Transaction;
import com.JavaProjectRest.V1.clientes.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
       // Transaction savedTransaction = transactionService.processTransaction(transaction);
       // return ResponseEntity.ok(savedTransaction);
       // return new ResponseEntity<>(clienteService.createCliente(cliente), HttpStatus.CREATED);

        return new ResponseEntity<>(transactionService.processTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

}
