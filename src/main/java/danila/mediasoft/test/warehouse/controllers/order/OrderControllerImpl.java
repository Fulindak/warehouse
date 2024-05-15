package danila.mediasoft.test.warehouse.controllers.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.CreateOrderResponse;
import danila.mediasoft.test.warehouse.dto.order.OrderResponse;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private final ConversionService conversionService;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse create(CreateOrderRequest orderRequest) {
        return CreateOrderResponse.builder()
                .id(orderService.create(orderRequest))
                .build();
    }

    @Override
    public OrderResponse update(@Valid Set<OrderProductRequest> products, UUID orderId) {
        return conversionService.convert(orderService.update(products, orderId), OrderResponse.class);
    }

    @Override
    public OrderResponse get(UUID orderId) {
        return conversionService.convert(orderService.get(orderId), OrderResponse.class);
    }

    @Override
    public void delete(UUID orderId) {
        orderService.delete(orderId);
    }

    @Override
    public OrderResponse confirm(UUID orderId) {
        return null;
    }

    @Override
    public OrderResponse updateStatus(UpdateStatusRequest updateStatusRequest, UUID orderId) {
        return conversionService.convert(
                orderService.updateStatus(updateStatusRequest, orderId),
                OrderResponse.class);
    }
}
