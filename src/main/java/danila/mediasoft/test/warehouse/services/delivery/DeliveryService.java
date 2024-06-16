package danila.mediasoft.test.warehouse.services.delivery;

import danila.mediasoft.test.warehouse.dto.delivery.DeliveryRequest;

import java.time.LocalDate;
import java.util.UUID;

public interface DeliveryService {
    LocalDate createDelivery(DeliveryRequest request);

    void cancelDelivery(UUID orderId);
}
