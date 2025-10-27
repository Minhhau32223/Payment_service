package com.swa.payment_domain_core.service;
import com.swa.payment_domain_core.entity.Payment;
import com.swa.payment_domain_core.events.*;

import java.time.ZonedDateTime;


public class PaymentDomainService {
    public PaymentInitiatedEvent initiatePayment(Payment payment) {
        // Business logic to initiate payment
        payment.initiate();
        return new PaymentInitiatedEvent(payment, ZonedDateTime.now());
    }
    public PaymentSucceedEvent completePayment(Payment payment) {
        // Business logic to complete payment
        payment.complete();
        return new PaymentSucceedEvent(payment, ZonedDateTime.now());
    }
    public PaymentFailedEvent failPayment(Payment payment, String reason) {
        // Business logic to handle payment failure
        payment.fail();
        return new PaymentFailedEvent(payment, reason, ZonedDateTime.now());
    }

//    public void rollbackPayment(Payment payment) {
//        // Business logic to rollback payment
//        payment.rollback();
//    }
}
