package com.JavaProjectRest.V1.transactions.service;

import com.JavaProjectRest.V1.transactions.adapter.ClienteClient;
import com.JavaProjectRest.V1.transactions.exception.SaldoInsuficienteException;
import com.JavaProjectRest.V1.transactions.model.Transaction;
import com.JavaProjectRest.V1.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

   // private final SqsClient sqsClient;
    private final String queueUrl = "https://sqs.REGION.amazonaws.com/ACCOUNT_ID/QUEUE_NAME"; // Reemplaza con la URL de tu SQS

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClienteClient clienteClient;


   /* public TransactionService(TransactionRepository transactionRepository, SqsClient sqsClient) {
        this.transactionRepository = transactionRepository;
        this.sqsClient = sqsClient;
    }
*/

    public void validationAndCreateTransaction(Transaction transaction) {
        BigDecimal saldoActual = clienteClient.obtenerSaldo(transaction.getClientId());
        if (saldoActual.compareTo(transaction.getAmount()) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
        // Registrar la transacción
        clienteClient.actualizarSaldo(transaction.getClientId(), saldoActual.subtract(transaction.getAmount()));
    }

    public Transaction processTransaction(Transaction transaction) {

        //sendMessageToQueue(transaction);
        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /*private void sendMessageToQueue(Transaction transaction) {
        try {
            String messageBody = String.format(
                    "{\"id\":%d,\"accountId\":\"%s\",\"amount\":%.2f,\"type\":\"%s\"}",
                    transaction.getId(),
                    transaction.getAccountId(),
                    transaction.getAmount(),
                    transaction.getType()
            );

            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();

            sqsClient.sendMessage(sendMessageRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error sending message to SQS: " + e.getMessage(), e);
        }
    }*/
}
