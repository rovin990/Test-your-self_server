package com.kick.it.kickit.entities.payments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "PYMNT_ORDR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String orderCreationId;
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
    private String status;
    private String validTIll="6"; //in months
    private String amount;
    private int attempts;
    private String currency;
    private String receipt;


    private LocalDate createdDate;
    private String createdBy;
    private LocalDate updatedDate;
    private String updatedBy;

    public PaymentOrder( String orderCreationId, String status, String validTIll, String amount, int attempts, String currency, String receipt) {
        this.orderCreationId = orderCreationId;
        this.status = status;
        this.validTIll = validTIll;
        this.amount = amount;
        this.attempts = attempts;
        this.currency = currency;
        this.receipt = receipt;
    }


}


