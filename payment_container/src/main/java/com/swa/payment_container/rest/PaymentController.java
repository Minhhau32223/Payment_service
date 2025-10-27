package com.swa.payment_container.rest;

import com.swa.payment_application_service.dto.*;
import com.swa.payment_application_service.ports.input.service.ProcessPaymentUseCase;
import org.springframework.http.ResponseEntity;
import com.swa.payment_domain_core.value_object.PaymentStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;

    public PaymentController(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    // POST /payments -> tạo payment mới
    @PostMapping
    public ResponseEntity<ProcessPaymentResponse> createPayment(@RequestBody ProcessPaymentCommand command) {
        ProcessPaymentResponse response = processPaymentUseCase.processPayment(command);
        return ResponseEntity.ok(response);
    }

    // GET /payments/{id} -> lấy trạng thái payment
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(@PathVariable String paymentId) {
        PaymentStatus status = processPaymentUseCase.getPaymentStatus(paymentId);

        PaymentStatusResponse response = new PaymentStatusResponse(
                java.util.UUID.fromString(paymentId), // UUID
                status.name(),                        // enum -> String
                "Payment status retrieved"            // message
        );

        return ResponseEntity.ok(response);
    }

}
