package danila.mediasoft.test.warehouse.config;

import danila.mediasoft.test.warehouse.config.model.NotificationMethods;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest.notification-service")
public record NotificationProperties(String host, NotificationMethods methods) {
}
