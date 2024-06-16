package danila.mediasoft.test.warehouse.config;

import danila.mediasoft.test.warehouse.config.model.DeliveryMethods;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest.delivery-service")
public record DeliveryProperties(String host, DeliveryMethods methods) {
}
