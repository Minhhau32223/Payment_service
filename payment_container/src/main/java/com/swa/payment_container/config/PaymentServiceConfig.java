package com.swa.payment_container.config;

import com.swa.payment_application_service.mapper.PaymentDtoMapper;
import com.swa.payment_application_service.ports.input.service.ProcessPaymentUseCase;
import com.swa.payment_application_service.handler.ProcessPaymentCommandHandler;
import com.swa.payment_application_service.ports.output.repository.PaymentRepository;
import com.swa.payment_application_service.ports.output.gateway.PaymentGateway;
import com.swa.payment_application_service.ports.output.publisher.PaymentEventPublisher;
import com.swa.payment_domain_core.service.PaymentDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceConfig {

    @Bean
    public ProcessPaymentUseCase processPaymentUseCase(
            PaymentRepository paymentRepository,
            PaymentGateway paymentGateway,
            PaymentEventPublisher paymentEventPublisher,
            PaymentDomainService paymentDomainService,
            PaymentDtoMapper paymentDtoMapper
    ) {
        return new ProcessPaymentCommandHandler(
                paymentRepository,
                paymentGateway,
                paymentEventPublisher,
                paymentDomainService
        );
    }
}
