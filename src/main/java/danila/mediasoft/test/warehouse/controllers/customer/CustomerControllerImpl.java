package danila.mediasoft.test.warehouse.controllers.customer;

import danila.mediasoft.test.warehouse.dto.customer.CreateCustomerRequest;
import danila.mediasoft.test.warehouse.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController{
    private final CustomerService customerService;
    @Override
    public Long create(CreateCustomerRequest customerRequest) {
        return customerService.create(customerRequest);
    }
}
