package com.swa.payment_dataaccess.entity;
import com.swa.payment_domain_core.value_object.PaymentMethod;
import com.swa.payment_domain_core.value_object.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID id;

    @Column(name = "order_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID orderId;

    @Column(name = "customer_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID customerId;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Column(name = "payment_method", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "failure_message", columnDefinition = "TEXT")
    private String failureMessage;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Enums for JPA
    public enum PaymentStatusEnum {
        PENDING,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    public enum PaymentMethodEnum {
        CREDIT_CARD,
        DEBIT_CARD,
        BANK_TRANSFER,
        E_WALLET,
        CASH_ON_DELIVERY
    }
}
