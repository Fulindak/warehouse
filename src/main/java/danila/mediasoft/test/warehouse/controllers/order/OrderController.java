package danila.mediasoft.test.warehouse.controllers.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.CreateOrderResponse;
import danila.mediasoft.test.warehouse.dto.order.OrderResponse;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/order")
public interface OrderController {
    @PostMapping()
    CreateOrderResponse create(@Valid @RequestBody CreateOrderRequest orderRequest);

    @PatchMapping("/{orderId}")
    OrderResponse update(@Valid @RequestBody Set<OrderProductRequest> products, @PathVariable UUID orderId);

    @GetMapping("/{orderId}")
    OrderResponse get(@PathVariable UUID orderId);

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID orderId);

    @PostMapping("/{orderId}/confirm")
    OrderResponse confirm(@PathVariable UUID orderId);

    @PatchMapping("/{orderId}/status")
    OrderResponse updateStatus(@Valid @RequestBody UpdateStatusRequest updateStatusRequest, @PathVariable UUID orderId);
}
