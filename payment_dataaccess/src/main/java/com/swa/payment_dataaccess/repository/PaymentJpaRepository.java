package com.swa.payment_dataaccess.repository;

import com.swa.payment_dataaccess.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, UUID> {

    Optional<PaymentJpaEntity> findByOrderId(UUID orderId);

    Optional<PaymentJpaEntity> findById(UUID paymentId);

    @Query("SELECT p FROM PaymentJpaEntity p WHERE p.customerId = :customerId ORDER BY p.createdAt DESC")
    Optional<PaymentJpaEntity> findLatestByCustomerId(@Param("customerId") UUID customerId);
}