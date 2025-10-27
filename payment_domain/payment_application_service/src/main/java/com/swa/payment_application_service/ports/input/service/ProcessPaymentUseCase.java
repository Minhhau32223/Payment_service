package com.swa.payment_application_service.ports.input.service;
import com.swa.payment_application_service.dto.ProcessPaymentCommand;
import com.swa.payment_application_service.dto.ProcessPaymentResponse;
import com.swa.payment_domain_core.value_object.PaymentStatus;

public interface ProcessPaymentUseCase {
    ProcessPaymentResponse processPayment(ProcessPaymentCommand command);
    PaymentStatus getPaymentStatus(String paymentId); // thêm method
}
