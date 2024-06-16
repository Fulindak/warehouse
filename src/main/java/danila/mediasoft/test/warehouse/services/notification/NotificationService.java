package danila.mediasoft.test.warehouse.services.notification;

import danila.mediasoft.test.warehouse.dto.notification.NotificationRequest;

public interface NotificationService {
    void sendMsg(NotificationRequest request);
}
