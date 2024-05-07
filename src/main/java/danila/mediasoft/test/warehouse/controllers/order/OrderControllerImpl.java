package danila.mediasoft.test.warehouse.controllers.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.CreateOrderResponse;
import danila.mediasoft.test.warehouse.dto.order.OrderResponse;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.UpdateOrderProductRequest;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<CreateOrderResponse> create(CreateOrderRequest orderRequest) {
        return new ResponseEntity<>(CreateOrderResponse.builder()
                .id(orderService.create(orderRequest))
                .build(),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderResponse> update(@Valid Set<UpdateOrderProductRequest> products, UUID id) {
        OrderResponse orderResponse = conversionService.convert(orderService.update(products, id), OrderResponse.class);
        return ResponseEntity.ok(orderResponse);
    }

    @Override
    public ResponseEntity<OrderResponse> get(UUID id) {
        OrderResponse orderResponse = conversionService.convert(orderService.get(id), OrderResponse.class);
        return ResponseEntity.ok(orderResponse);
    }

    @Override
    public void delete(UUID id) {
        orderService.delete(id);
    }

    @Override
    public ResponseEntity<OrderResponse> confirm(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<OrderResponse> updateStatus(UpdateStatusRequest updateStatusRequest, UUID id) {
        OrderResponse orderResponse = conversionService.convert(
                orderService.updateStatus(updateStatusRequest, id),
                OrderResponse.class);
        return ResponseEntity.ok(orderResponse);
    }
}
