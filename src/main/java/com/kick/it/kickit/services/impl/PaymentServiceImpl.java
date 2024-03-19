package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.Utility.HmacUtils;
import com.kick.it.kickit.Utility.Payment_Constant;
import com.kick.it.kickit.Utility.Security_Constant;
import com.kick.it.kickit.entities.payments.PaymentOrder;
import com.kick.it.kickit.repository.PaymentRepository;
import com.kick.it.kickit.services.PaymentService;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;


    @Override
    public String createOrder(PaymentOrder paymentOrder) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            paymentOrder.setCreatedDate(LocalDate.now());
            paymentOrder.setCreatedBy(authentication.getName());
            paymentOrder.setUsername(authentication.getName());
            paymentRepository.save(paymentOrder);
            return "Created";
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String successOrder(PaymentOrder paymentOrder) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            paymentOrder.setUpdatedDate(LocalDate.now());
            paymentOrder.setUpdatedBy(authentication.getName());
            paymentOrder.setUsername(authentication.getName());
            String generated_signature =generateSignature(paymentOrder.getOrderCreationId(),paymentOrder.getRazorpayPaymentId());
            System.out.println(generated_signature);
            System.out.println(paymentOrder.getRazorpaySignature());
            if (generated_signature.equalsIgnoreCase(paymentOrder.getRazorpaySignature())) {
                //the payment received is from an authentic source.

                System.out.println(generated_signature);
            }
            paymentRepository.save(paymentOrder);
            return "Success";
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PaymentOrder getOrder(String creationId) {
        return paymentRepository.findByOrderCreationId(creationId);
    }

    @Override
    public String generateReceipt() {
        return UUID.randomUUID().toString();
    }

    private String generateSignature(String orderId,String razorPayPaymentId){
        final String message=orderId+"|"+razorPayPaymentId;
        try{
            return HmacUtils.generateHmac256(message, Payment_Constant.PAYMENT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e){
            e.printStackTrace();
        }
        return null;
    }
}
