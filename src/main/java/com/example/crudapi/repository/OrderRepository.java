package com.example.crudapi.repository;

import com.example.crudapi.model.Order;
import com.example.crudapi.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerNameContainingIgnoreCase(String customerName);
}
