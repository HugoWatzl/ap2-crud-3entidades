package com.example.crudapi.observer;

import com.example.crudapi.model.Order;
import com.example.crudapi.model.OrderStatus;

/**
 * DESIGN PATTERN: Observer
 *
 * Interface que todos os observadores de pedido devem implementar.
 * É chamada sempre que o status de um Pedido é alterado.
 */
public interface OrderObserver {

    void onStatusChanged(Order order, OrderStatus oldStatus, OrderStatus newStatus);
}
