package com.example.crudapi.strategy;

import lombok.AllArgsConstructor;

/**
 * DESIGN PATTERN: Strategy — Implementação concreta
 * Aplica um desconto percentual sobre o valor original.
 */
@AllArgsConstructor
public class PercentageDiscountStrategy implements DiscountStrategy {

    private final double percentage;

    @Override
    public double apply(double originalValue) {
        return originalValue * (1 - percentage / 100.0);
    }

    @Override
    public String getDescription() {
        return "Desconto de " + (int) percentage + "%";
    }
}
