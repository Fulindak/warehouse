package danila.mediasoft.test.warehouse.services.customer;

import danila.mediasoft.test.warehouse.dto.customer.CreateCustomerRequest;
import danila.mediasoft.test.warehouse.entities.Customer;

public interface CustomerService {
    Customer findById(Long id);

    Long create(CreateCustomerRequest customerRequest);
}
