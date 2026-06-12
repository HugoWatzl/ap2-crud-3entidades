package com.example.crudapi.strategy;

/**
 * DESIGN PATTERN: Strategy — Implementação concreta
 * Nenhum desconto aplicado; retorna o valor original intacto.
 */
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double apply(double originalValue) {
        return originalValue;
    }

    @Override
    public String getDescription() {
        return "Sem desconto";
    }
}
