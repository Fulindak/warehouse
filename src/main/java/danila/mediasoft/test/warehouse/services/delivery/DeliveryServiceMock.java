package danila.mediasoft.test.warehouse.services.delivery;

import danila.mediasoft.test.warehouse.dto.delivery.DeliveryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "rest.delivery-service", name = "mock", havingValue = "true")
@Primary
public class DeliveryServiceMock implements DeliveryService{
    @Override
    public LocalDate createDelivery(DeliveryRequest request) {
        return LocalDate.now().plusDays(2);
    }

    @Override
    public void cancelDelivery(UUID orderId) {
        log.info("Delivery from order: {} cancel", orderId);
    }
}
