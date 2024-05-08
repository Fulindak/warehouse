package danila.mediasoft.test.warehouse.services.orderproduct;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.entities.OrderProduct;
import danila.mediasoft.test.warehouse.entities.OrderProductId;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrderProductService {
    Set<OrderProduct> create(Set<OrderProductRequest> orderProductRequests);
    Optional<OrderProduct> getById(OrderProductId id);
    OrderProduct create(OrderProductRequest orderProductRequest);
    Set<OrderProduct> update(Set<OrderProductRequest> orderProductRequests, UUID orderId);
    OrderProduct update(OrderProductRequest orderProductRequests, UUID orderId);
    Set<OrderProductDTO> getByOrderId(UUID id);
}
