package org.example.orderservice.controller;

import demo.grpc.generated.PriceResponse;
import org.example.orderservice.dto.OrderPriceRequest;
import org.example.orderservice.dto.OrderPriceResponse;
import org.example.orderservice.service.PricingGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final PricingGateway pricingGateway;

    public OrderController(PricingGateway pricingGateway) {
        this.pricingGateway = pricingGateway;
    }

    @PostMapping("/calculate-price")
    public OrderPriceResponse calculate(@RequestBody OrderPriceRequest request) {
        PriceResponse response = pricingGateway.getPrice(
                request.productName(),
                request.unitPrice(),
                request.quantity(),
                request.discountPercent()
        );

        return new OrderPriceResponse(
                response.getTotalWithoutDiscount(),
                response.getDiscountAmount(),
                response.getFinalTotal()
        );
    }
}
