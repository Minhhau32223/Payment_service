package com.swa.payment_messaging.publisher;

import com.swa.payment_application_service.ports.output.publisher.PaymentEventPublisher;
import com.swa.payment_domain_core.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentKafkaPublisher implements PaymentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_INITIATED = "payment_initiated";
    private static final String TOPIC_FAILED = "payment_failed";
    private static final String TOPIC_SUCCEED = "payment_succeed";

    @Override
    public void publish(PaymentInitiatedEvent event) {
        kafkaTemplate.send(TOPIC_INITIATED, event);
        System.out.println(" Sent PaymentInitiatedEvent to Kafka: " + event);
    }

    @Override
    public void publish(PaymentFailedEvent event) {
        kafkaTemplate.send(TOPIC_FAILED, event);
        System.out.println(" Sent PaymentFailedEvent to Kafka: " + event);
    }

    @Override
    public void publish(PaymentSucceedEvent event) {
        kafkaTemplate.send(TOPIC_SUCCEED, event);
        System.out.println(" Sent PaymentSucceedEvent to Kafka: " + event);
    }
}
