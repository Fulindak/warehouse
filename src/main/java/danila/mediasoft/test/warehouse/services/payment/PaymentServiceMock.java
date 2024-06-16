package danila.mediasoft.test.warehouse.services.payment;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@ConditionalOnProperty(prefix = "rest.payment-service", name = "mock", havingValue = "true")
@Primary
public class PaymentServiceMock implements PaymentService {
    private static final String ERROR_PAYMENT_STATUS = "FAILED";
    private static final String SUCCESS_PAYMENT_STATUS = "SUCCESS";

    @Override
    public String payOrder(UUID orderId, BigDecimal price, String accountNumber) {
        int randomNum = (int) ((Math.random() * (100 - 1)) + 1);
        if (randomNum > 10) {
            return SUCCESS_PAYMENT_STATUS;
        } else {
            return ERROR_PAYMENT_STATUS;
        }
    }
}
