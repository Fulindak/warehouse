package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.ProductType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {
}