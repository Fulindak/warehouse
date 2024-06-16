package danila.mediasoft.test.warehouse.dto.delivery;

import java.util.UUID;

public record DeliveryRequest(String deliveryAddress, UUID orderId) {
}
