package danila.mediasoft.test.warehouse.services.customer;

import danila.mediasoft.test.warehouse.dto.customer.CreateCustomerRequest;
import danila.mediasoft.test.warehouse.entities.Customer;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotFoundException;
import danila.mediasoft.test.warehouse.exceptions.ValueAlreadyExistsException;
import danila.mediasoft.test.warehouse.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer by " + id + " not found"));
    }

    @Override
    public Long create(CreateCustomerRequest customerRequest) {
        if (customerRepository.findByLogin(customerRequest.login()).isPresent()) {
            throw new ValueAlreadyExistsException("Customer by  login: " + customerRequest.login() + " already exist");
        }
        Customer customer = Customer.builder()
                .email(customerRequest.email())
                .login(customerRequest.login())
                .isActive(true)
                .build();
        return customerRepository.save(customer).getId();
    }
}
