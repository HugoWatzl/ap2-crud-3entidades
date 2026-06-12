package com.example.crudapi.strategy;

/**
 * DESIGN PATTERN: Strategy
 *
 * Interface que define o contrato para as estratégias de desconto.
 * Permite trocar o algoritmo de cálculo sem alterar o código do serviço.
 */
public interface DiscountStrategy {

    /**
     * Aplica o desconto sobre o valor original.
     *
     * @param originalValue valor bruto sem desconto
     * @return valor final após aplicação do desconto
     */
    double apply(double originalValue);

    /**
     * Descrição legível da estratégia aplicada.
     */
    String getDescription();
}
