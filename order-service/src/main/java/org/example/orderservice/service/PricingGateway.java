package org.example.orderservice.service;

import demo.grpc.generated.PriceRequest;
import demo.grpc.generated.PriceResponse;
import demo.grpc.generated.PriceServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class PricingGateway {

    private final PriceServiceGrpc.PriceServiceBlockingStub stub;

    public PricingGateway(PriceServiceGrpc.PriceServiceBlockingStub stub) {
        this.stub = stub;
    }

    public PriceResponse getPrice(String productName,
                                  double unitPrice,
                                  int quantity,
                                  double discountPercent) {

        PriceRequest request = PriceRequest.newBuilder()
                .setProductName(productName)
                .setUnitPrice(unitPrice)
                .setQuantity(quantity)
                .setDiscountPercent(discountPercent)
                .build();

        return stub.calculatePrice(request);
    }
}
