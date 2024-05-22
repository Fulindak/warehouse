package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.Order;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
                select o from Order o
                left join fetch o.products
                left join fetch o.customer
                where o.orderStatus in :statuses
            """)
    List<Order> findOrdersWithStatuses(@Param("statuses") List<OrderStatus> statuses);
}
