package com.swa.payment_messaging.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    @KafkaListener(topics = "order_payment_request", groupId = "payment-group")
    public void handlePaymentRequest(String message) {
        System.out.println("Received payment request from Kafka: " + message);
    }
}
