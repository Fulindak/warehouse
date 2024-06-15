package danila.mediasoft.test.warehouse.controllers.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.CreateOrderResponse;
import danila.mediasoft.test.warehouse.dto.order.OrderInfo;
import danila.mediasoft.test.warehouse.dto.order.OrderResponse;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/order")
public interface OrderController {
    @PostMapping()
    CreateOrderResponse create(@Valid @RequestBody CreateOrderRequest orderRequest);

    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    void update(@Valid @RequestBody Set<OrderProductRequest> products, @PathVariable UUID orderId);

    @GetMapping("/{orderId}")
    OrderResponse get(@PathVariable UUID orderId);

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID orderId);

    @PostMapping("/{orderId}/confirm")
    OrderResponse confirm(@PathVariable UUID orderId);

    @PatchMapping("/{orderId}/status")
    OrderResponse updateStatus(@Valid @RequestBody UpdateStatusRequest updateStatusRequest, @PathVariable UUID orderId);

    @GetMapping("/info")
    Map<UUID, List<OrderInfo>> getOrdersInfoGroupByProductId();
}
