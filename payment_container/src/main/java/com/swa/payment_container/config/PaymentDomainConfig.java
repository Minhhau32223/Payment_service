package com.swa.payment_container.config;

import com.swa.payment_domain_core.service.PaymentDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentDomainConfig {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainService();
    }
}
