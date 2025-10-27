package com.swa.payment_domain_core.events;
import com.swa.payment_domain_core.entity.Payment;
import java.time.ZonedDateTime;
public class PaymentSucceedEvent {
    private final Payment payment;
    private final ZonedDateTime createdAt;

    public PaymentSucceedEvent(Payment payment, ZonedDateTime createdAt) {
        this.payment = payment;
        this.createdAt = createdAt;
    }

    public Payment getPayment() {
        return payment;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
