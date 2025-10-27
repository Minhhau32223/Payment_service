package com.swa.payment_application_service.ports.output;

public interface OrderEventPublisher {
    void publishOrderCreated(String topic, String message);
}
