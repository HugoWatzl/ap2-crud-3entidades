package com.example.crudapi.strategy;

import org.springframework.stereotype.Component;

/**
 * DESIGN PATTERN: Strategy + Factory
 *
 * Fábrica responsável por retornar a implementação correta
 * de DiscountStrategy com base no DiscountType informado.
 * O OrderService depende apenas desta factory — princípio Open/Closed (SOLID).
 */
@Component
public class DiscountStrategyFactory {

    public DiscountStrategy getStrategy(DiscountType type) {
        return switch (type) {
            case NONE         -> new NoDiscountStrategy();
            case PERCENTAGE_10 -> new PercentageDiscountStrategy(10);
            case PERCENTAGE_20 -> new PercentageDiscountStrategy(20);
            case PERCENTAGE_50 -> new PercentageDiscountStrategy(50);
        };
    }
}
