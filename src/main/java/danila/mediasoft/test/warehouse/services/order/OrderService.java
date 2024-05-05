package danila.mediasoft.test.warehouse.services.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;

public interface OrderService {
    void createOrder(CreateOrderRequest orderRequest);
}
