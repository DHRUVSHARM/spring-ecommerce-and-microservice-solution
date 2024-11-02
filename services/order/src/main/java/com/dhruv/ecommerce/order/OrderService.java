package com.dhruv.ecommerce.order;

import com.dhruv.ecommerce.kafka.OrderConfirmation;
import com.dhruv.ecommerce.customer.CustomerClient;
import com.dhruv.ecommerce.exception.BusinessException;
import com.dhruv.ecommerce.kafka.OrderProducer;
import com.dhruv.ecommerce.orderline.OrderLineRequest;
import com.dhruv.ecommerce.orderline.OrderLineService;
import com.dhruv.ecommerce.payment.PaymentClient;
import com.dhruv.ecommerce.payment.PaymentRequest;
import com.dhruv.ecommerce.product.ProductClient;
import com.dhruv.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    // feign client for the customer
    private final CustomerClient customerClient;


    private final PaymentClient paymentClient;

    private final ProductClient productClient;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest request) {

        // so, the first step will be to find the customer , if not found we throw exception

        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(
                        () -> new BusinessException(
                                "Cannot create order:: No customer exists with the provided ID"));

        // make the purchase using the product microservice via the product rest client
        var purchasedProducts = productClient.purchaseProducts(request.products());

        // save the order
        var order = this.repository.save(mapper.toOrder(request));

        // take the products one by one and put them in the orderline
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }


        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // sending the order to the kafka broker , putting the order confirmation in the kafka producer
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}