package danila.mediasoft.test.warehouse.dto.notification;

import java.util.UUID;

public record NotificationRequest(String msg, UUID orderId, Long customerId) {
}
