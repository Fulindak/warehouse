package danila.mediasoft.test.warehouse.services.customer;

import danila.mediasoft.test.warehouse.dto.customer.CreateCustomerRequest;
import danila.mediasoft.test.warehouse.entities.Customer;

public interface CustomerService {
    Customer getCustomerById(Long id);

    Long create(CreateCustomerRequest customerRequest);
}
