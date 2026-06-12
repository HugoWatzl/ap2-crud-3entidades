package com.example.crudapi.observer;

import com.example.crudapi.model.Order;
import com.example.crudapi.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * DESIGN PATTERN: Observer — Observador concreto (Log)
 *
 * Registra no log toda mudança de status de pedido.
 * Implementa rastreabilidade sem acoplamento ao OrderService.
 */
@Slf4j
@Component
public class LogOrderObserver implements OrderObserver {

    @Override
    public void onStatusChanged(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        log.info("[LOG OBSERVER] {} | Pedido #{} do cliente '{}' | Status: {} → {}",
                LocalDateTime.now(),
                order.getId(),
                order.getCustomerName(),
                oldStatus,
                newStatus);
    }
}
