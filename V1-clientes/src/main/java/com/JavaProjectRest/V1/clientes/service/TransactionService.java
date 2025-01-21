package com.JavaProjectRest.V1.clientes.service;

import com.JavaProjectRest.V1.clientes.model.Transaction;
import com.JavaProjectRest.V1.clientes.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;

@Service
public class TransactionService {

    private final SqsClient sqsClient;
    private final String queueUrl = "https://sqs.REGION.amazonaws.com/ACCOUNT_ID/QUEUE_NAME"; // Reemplaza con la URL de tu SQS

    @Autowired
    private TransactionRepository transactionRepository;


    public TransactionService(TransactionRepository transactionRepository, SqsClient sqsClient) {
        this.transactionRepository = transactionRepository;
        this.sqsClient = sqsClient;
    }

    public Transaction processTransaction(Transaction transaction) {
        // Save the transaction in the database
        sendMessageToQueue(transaction);
        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    private void sendMessageToQueue(Transaction transaction) {
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
    }
}
