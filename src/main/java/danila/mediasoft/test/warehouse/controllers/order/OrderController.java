package danila.mediasoft.test.warehouse.controllers.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.CreateOrderResponse;
import danila.mediasoft.test.warehouse.dto.order.OrderResponse;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/order")
public interface OrderController {
    @PostMapping()
    ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody CreateOrderRequest orderRequest);

    @PatchMapping("/{orderId}")
    ResponseEntity<OrderResponse> update(@Valid @RequestBody Set<OrderProductRequest> products, @PathVariable UUID id);

    @GetMapping("/{orderId}")
    ResponseEntity<OrderResponse> get(@PathVariable UUID id);

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

    @PostMapping("/{orderId}/confirm")
    ResponseEntity<OrderResponse> confirm(@PathVariable UUID id);

    @PatchMapping("/{orderId}/status")
    ResponseEntity<OrderResponse> updateStatus(@Valid @RequestBody UpdateStatusRequest updateStatusRequest, @PathVariable UUID id);
}
