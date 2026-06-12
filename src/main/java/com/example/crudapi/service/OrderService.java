package com.example.crudapi.service;

import com.example.crudapi.dto.OrderItemRequestDTO;
import com.example.crudapi.dto.OrderRequestDTO;
import com.example.crudapi.exception.ResourceNotFoundException;
import com.example.crudapi.model.Order;
import com.example.crudapi.model.OrderItem;
import com.example.crudapi.model.OrderStatus;
import com.example.crudapi.model.Product;
import com.example.crudapi.observer.OrderEventPublisher;
import com.example.crudapi.repository.OrderRepository;
import com.example.crudapi.strategy.DiscountStrategy;
import com.example.crudapi.strategy.DiscountStrategyFactory;
import com.example.crudapi.strategy.DiscountType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    // DESIGN PATTERN: Strategy — injeção da factory de descontos
    private final DiscountStrategyFactory discountStrategyFactory;

    // DESIGN PATTERN: Observer — publicador de eventos de pedido
    private final OrderEventPublisher orderEventPublisher;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * Cria um novo pedido aplicando a estratégia de desconto selecionada.
     * DESIGN PATTERN: Strategy em ação — cálculo do total delegado à strategy.
     */
    @Transactional
    public Order create(OrderRequestDTO dto) {
        DiscountType discountType = dto.getDiscountType() != null ? dto.getDiscountType() : DiscountType.NONE;

        // Strategy: obtém a implementação correta de desconto
        DiscountStrategy strategy = discountStrategyFactory.getStrategy(discountType);
        log.info("Estratégia de desconto selecionada: {}", strategy.getDescription());

        Order order = Order.builder()
                .customerName(dto.getCustomerName())
                .status(OrderStatus.PENDING)
                .discountApplied(strategy.getDescription())
                .total(0.0)
                .items(new ArrayList<>())
                .build();

        // Monta os itens e calcula subtotal
        List<OrderItem> items = buildItems(dto.getItems(), order);
        double subtotal = items.stream().mapToDouble(OrderItem::getSubtotal).sum();

        // Strategy: aplica o desconto ao subtotal
        double total = strategy.apply(subtotal);

        order.setItems(items);
        order.setTotal(Math.round(total * 100.0) / 100.0);

        return orderRepository.save(order);
    }

    /**
     * Atualiza o status do pedido e notifica todos os observadores.
     * DESIGN PATTERN: Observer em ação — notificação disparada aqui.
     */
    @Transactional
    public Order updateStatus(Long id, OrderStatus newStatus) {
        Order order = findById(id);
        OrderStatus oldStatus = order.getStatus();

        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);

        // Observer: publica o evento para todos os observadores registrados
        orderEventPublisher.notifyStatusChanged(saved, oldStatus, newStatus);

        return saved;
    }

    @Transactional
    public void delete(Long id) {
        Order order = findById(id);
        orderRepository.delete(order);
    }

    // ── Métodos privados (Clean Code — funções pequenas e coesas) ──────────────

    private List<OrderItem> buildItems(List<OrderItemRequestDTO> itemDTOs, Order order) {
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequestDTO itemDTO : itemDTOs) {
            Product product = productService.findById(itemDTO.getProductId());
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
            items.add(item);
        }
        return items;
    }
}
