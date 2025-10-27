package com.swa.payment_application_service.ports.output.publisher;
import com.swa.payment_domain_core.events.*;
import com.swa.payment_application_service.ports.output.*;
public interface PaymentEventPublisher {
    void publish(PaymentInitiatedEvent event);
    void publish(PaymentFailedEvent event);
    void publish(PaymentSucceedEvent event);
}
