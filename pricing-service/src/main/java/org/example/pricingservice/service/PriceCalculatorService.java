package org.example.pricingservice.service;

import org.example.pricingservice.dto.Result;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculatorService {

    public Result calculate(double unitPrice, int quantity, double discountPercent) {
        double totalWithoutDiscount = unitPrice * quantity;
        double discountAmount = totalWithoutDiscount * discountPercent / 100.0;
        double finalTotal = totalWithoutDiscount - discountAmount;

        return new Result(totalWithoutDiscount, discountAmount, finalTotal);
    }
}
