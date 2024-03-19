package com.kick.it.kickit.controllers;

import com.kick.it.kickit.Utility.Payment_Constant;
import com.kick.it.kickit.entities.payments.PaymentOrder;
import com.kick.it.kickit.services.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestParam String amount)  {
        System.out.println(amount);
        try {
            RazorpayClient client = new RazorpayClient(Payment_Constant.PAYMENT_SECRET_KEY, Payment_Constant.PAYMENT_SECRET_SECRET);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",50000);
            orderRequest.put("currency","INR");
            orderRequest.put("receipt", "receipt#"+paymentService.generateReceipt());
            JSONObject notes = new JSONObject();
            notes.put("notes_key_1","Tea, Earl Grey, Hot");
            orderRequest.put("notes",notes);

            Order order = client.orders.create(orderRequest);

            String orderCreationId = order.toJson().getString("id");
            String status = order.toJson().getString("status");
            String validTIll = "6";
            String amount1 = String.valueOf(order.toJson().getInt("amount"));
            int attempts = order.toJson().getInt("attempts");
            String currency = order.toJson().getString("currency");
            String receipt = order.toJson().getString("receipt");

            PaymentOrder local = new PaymentOrder(orderCreationId,status,validTIll,amount1,attempts,currency,receipt);

            paymentService.createOrder(local);

            return  ResponseEntity.status(HttpStatus.OK).body(order.toString());


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }

    @PostMapping("/success")
    public ResponseEntity<?> saveOrderDetails(@RequestBody PaymentOrder paymentOrder){

        try{
            PaymentOrder local = paymentService.getOrder(paymentOrder.getOrderCreationId());
            local.setStatus("Success");
            local.setRazorpayOrderId(paymentOrder.getRazorpayOrderId());
            local.setRazorpayPaymentId(paymentOrder.getRazorpayPaymentId());
            local.setRazorpaySignature(paymentOrder.getRazorpaySignature());

            paymentService.successOrder(local);

            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
