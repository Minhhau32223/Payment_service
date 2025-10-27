package com.swa.payment_dataaccess.adapter;

import com.swa.payment_application_service.ports.output.gateway.PaymentGateway;
import com.swa.payment_dataaccess.entity.PaymentJpaEntity;
import com.swa.payment_dataaccess.mapper.PaymentDataAccessMapper;
import com.swa.payment_dataaccess.repository.PaymentJpaRepository;
import com.swa.payment_domain_core.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    /**
     * Lưu thông tin thanh toán vào database
     */
    @Override
    public void save(Payment payment) {
        PaymentJpaEntity entity = paymentDataAccessMapper.paymentToPaymentJpaEntity(payment,payment.getOrderId().getValue());
        paymentJpaRepository.save(entity);
    }

    /**
     * Thực hiện xử lý thanh toán (có thể là gọi API bên thứ 3 hoặc mock)
     * Ở đây giả lập thanh toán thành công.
     */
    @Override
    public boolean charge(Payment payment) {
        // TODO: Tích hợp cổng thanh toán thực tế (Stripe, PayPal, VNPay, v.v.)
        // Hiện tại ta giả lập rằng thanh toán luôn thành công.
        System.out.println("✅ Processing payment for order: " + payment.getOrderId().getValue()
                + " | amount: " + payment.getAmount().getAmount());
        return true;
    }
}
