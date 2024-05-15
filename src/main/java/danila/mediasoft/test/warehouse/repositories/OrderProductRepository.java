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
    @Query("""
                select new danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO (
                    op.id.productId,
                    p.name,
                    op.quantity,
                    op.price
                  )
                from OrderProduct op
                  join Product p on p.id = op.id.productId
                  join Order o on o.id = op.id.orderId
                where op.id.orderId = :orderId
                and o.customer.id = :customerId
            """)
    Set<OrderProductDTO> getProjectionsByOrderIdAndCustomerId(UUID orderId, Long customerId);
}
