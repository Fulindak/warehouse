package danila.mediasoft.test.warehouse.services.customer;

import danila.mediasoft.test.warehouse.entities.Customer;

public interface CustomerService {
    Customer findById(Long id);
}
