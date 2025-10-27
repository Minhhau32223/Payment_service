package com.swa.payment_domain_core.events;

import com.swa.payment_domain_core.entity.Payment;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentInitiatedEvent {
    private final Payment payment;
    private final ZonedDateTime createdAt;
}
