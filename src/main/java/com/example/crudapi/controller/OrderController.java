package com.example.crudapi.controller;

import com.example.crudapi.dto.OrderRequestDTO;
import com.example.crudapi.dto.OrderStatusUpdateDTO;
import com.example.crudapi.model.Order;
import com.example.crudapi.model.OrderStatus;
import com.example.crudapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "CRUD de Pedidos | Strategy (desconto) + Observer (notificação de status)")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Listar todos os pedidos")
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Order> findById(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Filtrar pedidos por status",
               description = "Status válidos: PENDING | CONFIRMED | SHIPPED | DELIVERED | CANCELLED")
    public ResponseEntity<List<Order>> findByStatus(
            @Parameter(description = "Status do pedido") @PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.findByStatus(status));
    }

    @PostMapping
    @Operation(
            summary = "Criar novo pedido",
            description = """
                    Cria um pedido com os itens informados e aplica desconto via **Strategy Pattern**.
                    
                    `discountType` aceita: `NONE`, `PERCENTAGE_10`, `PERCENTAGE_20`, `PERCENTAGE_50`
                    
                    O total já retorna com o desconto aplicado.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Atualizar status do pedido",
            description = """
                    Altera o status do pedido e dispara o **Observer Pattern**:
                    - **LogOrderObserver**: registra a mudança no log
                    - **StockOrderObserver**: simula reserva/devolução de estoque
                    
                    Status válidos: `PENDING` → `CONFIRMED` → `SHIPPED` → `DELIVERED` | `CANCELLED`
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado e observadores notificados"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Order> updateStatus(
            @Parameter(description = "ID do pedido") @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateDTO dto) {
        return ResponseEntity.ok(orderService.updateStatus(id, dto.getStatus()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido removido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
