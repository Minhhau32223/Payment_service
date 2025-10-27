package com.swa.payment_dataaccess.context;

import java.util.UUID;

public class PaymentContext {
    private static final ThreadLocal<UUID> customerIdHolder = new ThreadLocal<>();

    public static void setCustomerId(UUID customerId) {
        customerIdHolder.set(customerId);
    }

    public static UUID getCustomerId() {
        return customerIdHolder.get();
    }

    public static void clear() {
        customerIdHolder.remove();
    }
}
