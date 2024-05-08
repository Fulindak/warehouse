package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO;
import danila.mediasoft.test.warehouse.entities.OrderProduct;
import danila.mediasoft.test.warehouse.entities.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    @Query("SELECT new danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO(p.product.id, p.product.name, p.quantity, p.price) FROM OrderProduct p WHERE p.order.id = :orderId")
    Set<OrderProductDTO> findByOrderId(UUID orderId);
}
