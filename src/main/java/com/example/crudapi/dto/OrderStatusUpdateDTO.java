package com.example.crudapi.dto;

import com.example.crudapi.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusUpdateDTO {

    @NotNull(message = "Status é obrigatório")
    private OrderStatus status;
}
