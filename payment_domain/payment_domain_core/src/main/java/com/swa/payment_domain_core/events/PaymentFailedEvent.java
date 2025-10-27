package com.swa.payment_domain_core.events;
import com.swa.payment_domain_core.entity.Payment;
import com.swa.payment_domain_core.value_object.PaymentID;

import java.time.ZonedDateTime;


public class PaymentFailedEvent {
    private final Payment payment;
    private final ZonedDateTime createdAt;
    private final String reason;

    public PaymentFailedEvent(Payment payment, String reason, ZonedDateTime createdAt) {
        this.payment = payment;
        this.reason = reason;
        this.createdAt = createdAt;
    }


    public Payment getPayment() { return payment; }
    public String getReason() { return reason; }
    public ZonedDateTime getCreatedAt() { return createdAt; }
}
