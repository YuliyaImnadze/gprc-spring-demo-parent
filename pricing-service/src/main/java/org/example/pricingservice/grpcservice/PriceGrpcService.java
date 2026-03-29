package org.example.pricingservice.grpcservice;

import demo.grpc.generated.PriceRequest;
import demo.grpc.generated.PriceResponse;
import io.grpc.stub.StreamObserver;
import org.example.pricingservice.dto.Result;
import org.example.pricingservice.service.PriceCalculatorService;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class PriceGrpcService extends demo.grpc.generated.PriceServiceGrpc.PriceServiceImplBase {

    private final PriceCalculatorService calculatorService;

    public PriceGrpcService(PriceCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
    public void calculatePrice(PriceRequest request, StreamObserver<PriceResponse> responseObserver) {
        Result result = calculatorService.calculate(
                request.getUnitPrice(),
                request.getQuantity(),
                request.getDiscountPercent()
        );

        PriceResponse response = PriceResponse.newBuilder()
                .setTotalWithoutDiscount(result.totalWithoutDiscount())
                .setDiscountAmount(result.discountAmount())
                .setFinalTotal(result.finalTotal())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
