package danila.mediasoft.test.warehouse.services.payment;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {
    String payOrder(UUID orderId, BigDecimal price, String accountNumber);
}
