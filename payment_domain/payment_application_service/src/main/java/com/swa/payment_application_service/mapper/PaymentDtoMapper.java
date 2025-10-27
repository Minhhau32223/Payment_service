package com.swa.payment_application_service.mapper;

import com.swa.payment_application_service.dto.ProcessPaymentCommand;
import com.swa.payment_application_service.dto.ProcessPaymentResponse;
import com.swa.payment_domain_core.entity.Payment;
import com.swa.payment_domain_core.value_object.Money;
import com.swa.payment_domain_core.value_object.PaymentID;
import com.swa.payment_domain_core.value_object.PaymentMethod;
import com.swa.payment_domain_core.value_object.PaymentStatus;
import com.swa.payment_domain_core.value_object.OrderID;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class PaymentDtoMapper {

    /**
     * Chuyển từ DTO yêu cầu thanh toán sang Domain Entity
     */
    public Payment toDomain(ProcessPaymentCommand command) {
        return Payment.builder()
                .paymentId(new PaymentID(UUID.randomUUID()))
                .orderId(new OrderID(command.getOrderId()) )
                .amount(new Money(command.getAmount()))
                .paymentMethod(PaymentMethod.valueOf(command.getPaymentMethod().toUpperCase()))
                .paymentStatus(PaymentStatus.PENDING)
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Chuyển từ Domain Entity sang DTO phản hồi thanh toán
     */
    public ProcessPaymentResponse toResponse(Payment payment) {
        return new ProcessPaymentResponse(
                payment.getId().getValue(),
                "Payment " + payment.getStatus().name().toLowerCase(),
                payment.getStatus().name()
        );
    }
}
