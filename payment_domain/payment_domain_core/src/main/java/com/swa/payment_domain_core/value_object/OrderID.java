package com.swa.payment_domain_core.value_object;
import java.util.UUID;
import lombok.Value;
@Value
public class OrderID  {
    private final UUID value;
    public static  OrderID getPaymentID() {
        return new OrderID(UUID.randomUUID());
    }
}
