package com.dhruv.ecommerce.payment;

import com.dhruv.ecommerce.customer.CustomerResponse;
import com.dhruv.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}