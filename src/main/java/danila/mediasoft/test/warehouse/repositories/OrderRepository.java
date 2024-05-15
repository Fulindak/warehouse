package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("from Order o " +
            "left join fetch o.products " +
            "where o.id = :orderId")
    Optional<Order> findByIdFetchOrderedProducts(UUID orderId);
}
