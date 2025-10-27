package com.swa.payment_domain_core.value_object;

import java.util.UUID;
import lombok.Getter;
import lombok.EqualsAndHashCode;

@Getter
@EqualsAndHashCode
public class PaymentID {

    private final UUID id;

    // ✅ Constructor nhận UUID
    public PaymentID(UUID id) {
        this.id = id;
    }
    public PaymentID(String id) {
        this.id= UUID.fromString(id);
    }

    // ✅ Tạo ID mới ngẫu nhiên
    public static PaymentID create() {
        return new PaymentID(UUID.randomUUID());
    }

    public UUID getValue() {
        return id;
    }
}
