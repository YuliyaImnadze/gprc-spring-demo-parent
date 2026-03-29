package org.example.orderservice.dto;

public record OrderPriceRequest(String productName,
                                double unitPrice,
                                int quantity,
                                double discountPercent) {
}
