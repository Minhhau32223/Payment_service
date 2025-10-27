package com.swa.payment_domain_core.entity;

import com.swa.payment_domain_core.value_object.*;
import lombok.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Payment {
    private PaymentID paymentId;
    private OrderID orderId;
    private Money amount;                // ✅ Đổi từ Amount → Money
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private Instant createdAt;

    public void initiate() {
        if (this.paymentStatus == PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is already initiated.");
        }
        this.paymentStatus = PaymentStatus.PENDING;
        this.createdAt = Instant.now();
    }

    public void complete() {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not in a state that can be completed.");
        }
        this.paymentStatus = PaymentStatus.COMPLETED;
    }

    public void fail() {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not in a state that can be failed.");
        }
        this.paymentStatus = PaymentStatus.FAILED;
    }

    // ✅ Các getter cần thiết cho mapper
    public PaymentID getId() {
        return paymentId;
    }

    public Money getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return paymentStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
