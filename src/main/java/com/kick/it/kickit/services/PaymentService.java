package com.kick.it.kickit.services;

import com.kick.it.kickit.entities.payments.PaymentOrder;

public interface PaymentService {

    public String createOrder(PaymentOrder paymentOrder);

    public String successOrder(PaymentOrder paymentOrder);

    public PaymentOrder getOrder(String findByOrderCreationId);

    public String generateReceipt();
}
