package com.swa.payment_application_service.ports.output.gateway;
import com.swa.payment_domain_core.entity.Payment;
public interface PaymentGateway {
    void save(Payment payment);

    boolean charge(Payment payment);
}
