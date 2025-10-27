package com.swa.payment_dataaccess.adapter;

import com.swa.payment_dataaccess.context.PaymentContext;
import com.swa.payment_dataaccess.entity.PaymentJpaEntity;
import com.swa.payment_dataaccess.mapper.PaymentDataAccessMapper;
import com.swa.payment_dataaccess.repository.PaymentJpaRepository;
import com.swa.payment_application_service.ports.output.repository.PaymentRepository;
import com.swa.payment_domain_core.entity.Payment;
import com.swa.payment_domain_core.value_object.OrderID;
import com.swa.payment_domain_core.value_object.PaymentID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    @Override
    @Transactional
    public Payment save(Payment payment) {
        UUID customerId = PaymentContext.getCustomerId();
        if (customerId == null) {
            throw new IllegalStateException("Customer ID not found in context. Please set it before saving payment.");
        }

        log.info("Saving payment with id: {} for customer: {}", payment.getPaymentId().getId(), customerId);

        PaymentJpaEntity paymentJpaEntity = paymentDataAccessMapper
                .paymentToPaymentJpaEntity(payment, customerId);

        PaymentJpaEntity savedEntity = paymentJpaRepository.save(paymentJpaEntity);

        log.info("Payment saved successfully with id: {}", savedEntity.getId());

        return paymentDataAccessMapper.paymentJpaEntityToPayment(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findById(PaymentID paymentID) {
        log.info("Finding payment by id: {}", paymentID.getId());

        return paymentJpaRepository.findById(paymentID.getId())
                .map(entity -> {
                    log.info("Payment found with id: {}", entity.getId());
                    return paymentDataAccessMapper.paymentJpaEntityToPayment(entity);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findByOrderId(OrderID orderID) {
        log.info("Finding payment by order id: {}", orderID.getValue());

        return paymentJpaRepository.findByOrderId(orderID.getValue())
                .map(entity -> {
                    log.info("Payment found for order id: {}", orderID.getValue());
                    return paymentDataAccessMapper.paymentJpaEntityToPayment(entity);
                });
    }
}
