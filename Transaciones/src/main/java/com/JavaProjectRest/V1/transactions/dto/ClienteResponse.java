package com.JavaProjectRest.V1.transactions.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClienteResponse {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String accountType;
    private BigDecimal balance;
}