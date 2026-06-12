package com.example.crudapi.observer;

import com.example.crudapi.model.Order;
import com.example.crudapi.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DESIGN PATTERN: Observer — Subject (Publicador)
 *
 * Gerencia a lista de observadores e notifica todos quando
 * o status de um pedido é alterado.
 * O Spring injeta automaticamente todos os beans que implementam OrderObserver.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final List<OrderObserver> observers;

    public void notifyStatusChanged(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        log.info(">>> Publicando evento: Pedido #{} — {} → {}", order.getId(), oldStatus, newStatus);
        observers.forEach(observer -> observer.onStatusChanged(order, oldStatus, newStatus));
    }
}
