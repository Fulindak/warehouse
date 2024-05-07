package danila.mediasoft.test.warehouse.services.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.OrderDTO;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.UpdateOrderProductRequest;

import java.util.Set;
import java.util.UUID;

public interface OrderService {
    UUID create(CreateOrderRequest orderRequest);

    OrderDTO update(Set<UpdateOrderProductRequest> products, UUID id);

    OrderDTO get(UUID id);

    void delete(UUID id);

    OrderDTO updateStatus(UpdateStatusRequest updateStatusRequest, UUID id);
}
