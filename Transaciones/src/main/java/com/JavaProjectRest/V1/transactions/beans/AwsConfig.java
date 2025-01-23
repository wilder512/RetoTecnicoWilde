package com.JavaProjectRest.V1.transactions.beans;

import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.sqs.SqsClient;

//@Configuration
public class AwsConfig {
    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder().build();
    }
}
