package com.dhruv.ecommerce.kafka;

import com.dhruv.ecommerce.customer.CustomerResponse;
import com.dhruv.ecommerce.order.PaymentMethod;
import com.dhruv.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}