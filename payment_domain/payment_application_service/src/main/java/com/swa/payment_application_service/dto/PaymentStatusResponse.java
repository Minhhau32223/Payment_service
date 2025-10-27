package com.swa.payment_application_service.dto;
import java.util.UUID;
import lombok.Getter;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Getter
public class PaymentStatusResponse {
    private final UUID paymentTrackingID;
    private final String paymentStatus;
    private final String message;
}
