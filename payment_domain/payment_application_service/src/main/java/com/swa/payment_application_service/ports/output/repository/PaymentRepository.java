package com.swa.payment_application_service.ports.output.repository;
import com.swa.payment_domain_core.entity.Payment;
import com.swa.payment_domain_core.value_object.*;
import java.util.Optional;
public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(PaymentID paymentID);
    Optional<Payment> findByOrderId(OrderID orderID);
}
