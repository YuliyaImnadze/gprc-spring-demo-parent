package org.example.orderservice.dto;

public record OrderPriceResponse(double totalWithoutDiscount,
                                 double discountAmount,
                                 double finalTotal) {
}
