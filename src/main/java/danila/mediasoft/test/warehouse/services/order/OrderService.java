package danila.mediasoft.test.warehouse.services.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.OrderDTO;
import danila.mediasoft.test.warehouse.dto.order.OrderInfo;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface OrderService {
    UUID create(CreateOrderRequest orderRequest);

    void update(Set<OrderProductRequest> products, UUID orderId);

    OrderDTO get(UUID orderId);

    void delete(UUID orderId);

    OrderDTO updateStatus(UpdateStatusRequest updateStatusRequest, UUID orderId);

    Map<UUID, List<OrderInfo>> getOrdersInfoGroupByProductId();
}



