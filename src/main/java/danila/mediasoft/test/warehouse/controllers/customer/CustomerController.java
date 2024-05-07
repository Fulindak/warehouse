package danila.mediasoft.test.warehouse.controllers.customer;

import danila.mediasoft.test.warehouse.dto.customer.CreateCustomerRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/customer")
public interface CustomerController {
    @PostMapping()
    Long create(@Valid @RequestBody CreateCustomerRequest customerRequest);
}
