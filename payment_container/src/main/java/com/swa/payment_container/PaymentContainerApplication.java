package com.swa.payment_container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.swa")
public class PaymentContainerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentContainerApplication.class, args);
    }
}
