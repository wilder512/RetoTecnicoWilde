package com.JavaProjectRest.V1.clientes.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {
    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder().build();
    }
}
