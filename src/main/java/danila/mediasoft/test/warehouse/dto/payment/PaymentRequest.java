package danila.mediasoft.test.warehouse.dto.payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(UUID orderId, BigDecimal price, String accountNumber) {
}
