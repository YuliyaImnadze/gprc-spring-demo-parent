package org.example.pricingservice.dto;

public record Result(
        double totalWithoutDiscount,
        double discountAmount,
        double finalTotal
) {
}
