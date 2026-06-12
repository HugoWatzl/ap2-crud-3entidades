package com.example.crudapi.observer;

import com.example.crudapi.model.Order;
import com.example.crudapi.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * DESIGN PATTERN: Observer — Observador concreto (Estoque)
 *
 * Reage a mudanças de status para simular controle de estoque:
 * - CONFIRMED: reserva os itens do pedido
 * - CANCELLED: devolve os itens ao estoque
 */
@Slf4j
@Component
public class StockOrderObserver implements OrderObserver {

    @Override
    public void onStatusChanged(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        if (newStatus == OrderStatus.CONFIRMED && oldStatus == OrderStatus.PENDING) {
            log.info("[STOCK OBSERVER] Pedido #{} confirmado — reservando {} item(ns) no estoque.",
                    order.getId(), order.getItems().size());
            order.getItems().forEach(item ->
                    log.info("  → Reservado: {} x '{}'", item.getQuantity(), item.getProduct().getName()));
        }

        if (newStatus == OrderStatus.CANCELLED) {
            log.info("[STOCK OBSERVER] Pedido #{} cancelado — devolvendo {} item(ns) ao estoque.",
                    order.getId(), order.getItems().size());
            order.getItems().forEach(item ->
                    log.info("  ← Devolvido: {} x '{}'", item.getQuantity(), item.getProduct().getName()));
        }
    }
}
