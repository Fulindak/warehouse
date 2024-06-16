package danila.mediasoft.test.warehouse.services.notification;

import danila.mediasoft.test.warehouse.dto.notification.NotificationRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "rest.notification-service", name = "mock", havingValue = "true")
@Primary
public class NotificationServiceMock implements NotificationService{
    @Override
    public void sendMsg(NotificationRequest request) {
        // Empty method
    }
}
