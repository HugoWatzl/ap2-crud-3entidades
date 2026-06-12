package com.example.crudapi.dto;

import com.example.crudapi.strategy.DiscountType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    @NotBlank(message = "Nome do cliente é obrigatório")
    private String customerName;

    /**
     * Tipo de desconto — usado pelo padrão Strategy.
     * Valores: NONE | PERCENTAGE_10 | PERCENTAGE_20 | PERCENTAGE_50
     */
    private DiscountType discountType = DiscountType.NONE;

    @NotEmpty(message = "O pedido deve ter pelo menos 1 item")
    @Valid
    private List<OrderItemRequestDTO> items;
}
