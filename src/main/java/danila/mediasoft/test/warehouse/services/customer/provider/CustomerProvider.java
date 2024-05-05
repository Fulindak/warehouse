package danila.mediasoft.test.warehouse.services.customer.provider;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@SessionScope
@Component
public class CustomerProvider {
    private Long id;
}
