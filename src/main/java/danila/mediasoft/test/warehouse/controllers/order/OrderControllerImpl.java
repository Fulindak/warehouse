package danila.mediasoft.test.warehouse.controllers.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController{
    private final OrderService orderService;
    @Override
    public void createOrder(CreateOrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
    }
}
