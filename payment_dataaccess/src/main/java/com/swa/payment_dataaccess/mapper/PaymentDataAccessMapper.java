package com.swa.payment_dataaccess.mapper;

import com.swa.payment_dataaccess.entity.PaymentJpaEntity;
import com.swa.payment_domain_core.entity.Payment;
import com.swa.payment_domain_core.value_object.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataAccessMapper {

    /**
     * Convert Domain Payment to JPA Entity
     * Note: customerId sẽ được truyền từ command, không có trong Payment domain
     */
    public PaymentJpaEntity paymentToPaymentJpaEntity(Payment payment, UUID customerId) {
        return PaymentJpaEntity.builder()
                .id(payment.getPaymentId().getId())
                .orderId(payment.getOrderId().getValue())
                .customerId(customerId)
                .amount(payment.getAmount().getAmount())
                .paymentStatus(mapPaymentStatus(payment.getPaymentStatus()))
                .paymentMethod(mapPaymentMethod(payment.getPaymentMethod()))
                .build();
    }

    /**
     * Convert JPA Entity to Domain Payment
     */
    public Payment paymentJpaEntityToPayment(PaymentJpaEntity entity) {
        return Payment.builder()
                .paymentId(new PaymentID(entity.getId()))
                .orderId(new OrderID(entity.getOrderId()))
                .amount(new Money(entity.getAmount()))
                .paymentStatus(mapPaymentStatusEnum(entity.getPaymentStatus()))
                .paymentMethod(mapPaymentMethodEnum(entity.getPaymentMethod()))
                .build();
    }

    // Mapping helpers
    private PaymentJpaEntity.PaymentStatusEnum mapPaymentStatus(PaymentStatus status) {
        if (status == null) {
            return null;
        }
        return switch (status) {
            case PENDING -> PaymentJpaEntity.PaymentStatusEnum.PENDING;
            case COMPLETED -> PaymentJpaEntity.PaymentStatusEnum.COMPLETED;
            case FAILED -> PaymentJpaEntity.PaymentStatusEnum.FAILED;
            case CANCELLED -> PaymentJpaEntity.PaymentStatusEnum.CANCELLED;
            case NOT_FOUND -> throw new IllegalArgumentException("Unexpected value: " + status);
        };
    }

    private PaymentStatus mapPaymentStatusEnum(PaymentJpaEntity.PaymentStatusEnum status) {
        if (status == null) {
            return null;
        }
        return switch (status) {
            case PENDING -> PaymentStatus.PENDING;
            case COMPLETED -> PaymentStatus.COMPLETED;
            case FAILED -> PaymentStatus.FAILED;
            case CANCELLED -> PaymentStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Unexpected value: " + status);
        };
    }

    private PaymentJpaEntity.PaymentMethodEnum mapPaymentMethod(PaymentMethod method) {
        if (method == null) {
            return null;
        }
        return switch (method) {
            case CREDIT_CARD -> PaymentJpaEntity.PaymentMethodEnum.CREDIT_CARD;
            case DEBIT_CARD -> PaymentJpaEntity.PaymentMethodEnum.DEBIT_CARD;
            case BANK_TRANSFER -> PaymentJpaEntity.PaymentMethodEnum.BANK_TRANSFER;
            case E_WALLET -> PaymentJpaEntity.PaymentMethodEnum.E_WALLET;
            case CASH_ON_DELIVERY -> PaymentJpaEntity.PaymentMethodEnum.CASH_ON_DELIVERY;
            default -> throw new IllegalArgumentException("Unexpected value: " + method);
        };
    }

    private PaymentMethod mapPaymentMethodEnum(PaymentJpaEntity.PaymentMethodEnum method) {
        if (method == null) {
            return null;
        }
        return switch (method) {
            case CREDIT_CARD -> PaymentMethod.CREDIT_CARD;
            case DEBIT_CARD -> PaymentMethod.DEBIT_CARD;
            case BANK_TRANSFER -> PaymentMethod.BANK_TRANSFER;
            case E_WALLET -> PaymentMethod.E_WALLET;
            case CASH_ON_DELIVERY -> PaymentMethod.CASH_ON_DELIVERY;
            default -> throw new IllegalArgumentException("Unexpected value: " + method);
        };
    }
}