package com.JavaProjectRest.V1.transactions.adapter;

import com.JavaProjectRest.V1.transactions.dto.ClienteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class ClienteClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;



    public ClienteClient(RestTemplate restTemplate, @Value("${cliente.service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl= baseUrl;
    }

    public BigDecimal obtenerSaldo(Long clienteId) {
        String url = String.format("%s/clientes/%d", baseUrl, clienteId);
            ClienteResponse clienteResponse = restTemplate.getForObject(url, ClienteResponse.class);
        if (clienteResponse == null) {
            throw new RuntimeException("No se pudo obtener la información del cliente");
        }
        return clienteResponse.getBalance();
    }




  //  public void actualizarSaldo(Long clienteId, BigDecimal nuevoSaldo) {
  //  restTemplate.put(baseUrl+ "/clientes/" + clienteId + "/saldo", nuevoSaldo);
   // }

    public void actualizarSaldo(Long clienteId, BigDecimal nuevoSaldo) {
        String url = String.format("%s/clientes/%d/saldo", baseUrl, clienteId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BigDecimal> request = new HttpEntity<>(nuevoSaldo, headers);
        restTemplate.put(url, request);
    }
}