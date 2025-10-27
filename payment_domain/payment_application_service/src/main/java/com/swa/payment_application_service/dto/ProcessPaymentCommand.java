package com.swa.payment_application_service.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.math.BigDecimal;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessPaymentCommand {
    @NotNull(message = "Payment ID cannot be null")
    private UUID paymentId;
    @NotNull(message = "Order ID cannot be null")
    private UUID orderId;

    @NotBlank(message = "Customer ID cannot be blank")
    private String customerId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount")
    private BigDecimal amount;
    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod; // e.g., "CREDIT_CARD", "PAYPAL"
    // For PAYPAL
}
