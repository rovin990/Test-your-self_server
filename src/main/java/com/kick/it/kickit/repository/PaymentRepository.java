package com.kick.it.kickit.repository;

import com.kick.it.kickit.entities.payments.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentOrder,Long> {

    public PaymentOrder findByOrderCreationId(String orderCreationId);
}
