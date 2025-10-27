package com.swa.payment_application_service.handler;

import com.swa.payment_application_service.dto.*;
import com.swa.payment_application_service.ports.input.service.ProcessPaymentUseCase;
import com.swa.payment_application_service.ports.output.gateway.PaymentGateway;
import com.swa.payment_application_service.ports.output.publisher.PaymentEventPublisher;
import com.swa.payment_application_service.ports.output.repository.PaymentRepository;
import com.swa.payment_domain_core.entity.Payment;
import com.swa.payment_domain_core.events.*;
import com.swa.payment_domain_core.service.PaymentDomainService;
import com.swa.payment_domain_core.value_object.*;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessPaymentCommandHandler implements ProcessPaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;
    private final PaymentEventPublisher eventPublisher;
    private final PaymentDomainService paymentDomainService;

    @Override
    public PaymentStatus getPaymentStatus(String paymentId) {
        return paymentRepository.findById(new PaymentID(paymentId))
                .map(Payment::getStatus)
                .orElse(PaymentStatus.NOT_FOUND);
    }

    @Override
    public ProcessPaymentResponse processPayment(ProcessPaymentCommand command) {
        try {
            // ✅ 1️⃣ Tạo Payment entity từ command
            Payment payment = Payment.builder()
                    .paymentId(new PaymentID(command.getPaymentId()))
                    .orderId(new OrderID(command.getOrderId()))
                    .amount(new Money(command.getAmount()))
                    .paymentMethod(PaymentMethod.valueOf(command.getPaymentMethod()))
                    // Initial status
                    .build();

            // ✅ 2️⃣ Khởi tạo Payment và raise event
            PaymentInitiatedEvent initiatedEvent = paymentDomainService.initiatePayment(payment);
            eventPublisher.publish(initiatedEvent);

            // ✅ 3️⃣ Thực hiện thanh toán qua gateway
            boolean success = paymentGateway.charge(payment);

            if (success) {
                // ✅ 4️⃣ Thanh toán thành công
                PaymentSucceedEvent succeedEvent = paymentDomainService.completePayment(payment);
                eventPublisher.publish(succeedEvent);
                paymentRepository.save(payment);

                return new ProcessPaymentResponse(
                        payment.getPaymentId().getValue(),
                        "Payment processed successfully",
                        "COMPLETED"
                );
            } else {
                // ✅ 5️⃣ Thanh toán thất bại từ gateway
                String reason = "Payment gateway declined the transaction";
                PaymentFailedEvent failedEvent = paymentDomainService.failPayment(payment, reason);
                eventPublisher.publish(failedEvent);
                paymentRepository.save(payment); // Lưu trạng thái FAILED

                return new ProcessPaymentResponse(
                        payment.getPaymentId().getValue(),
                        reason,
                        "FAILED"
                );
            }

        } catch (Exception e) {
            // ✅ 6️⃣ Xử lý exception
            String errorMessage = "Payment processing error: " + e.getMessage();

            // Tạo PaymentFailedEvent với lý do từ exception
            PaymentFailedEvent errorEvent = new PaymentFailedEvent(
                    null, // payment có thể null nếu lỗi xảy ra sớm
                    errorMessage,
                    java.time.ZonedDateTime.now()
            );
            eventPublisher.publish(errorEvent);

            return new ProcessPaymentResponse(
                    command.getPaymentId(),
                    errorMessage,
                    "ERROR"
            );
        }
    }
}