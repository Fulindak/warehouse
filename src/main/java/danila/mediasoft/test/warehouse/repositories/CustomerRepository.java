package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
