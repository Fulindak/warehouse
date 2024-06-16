package danila.mediasoft.test.warehouse.config;

import danila.mediasoft.test.warehouse.config.model.PaymentMethods;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest.payment-service")
public record PaymentProperties(String host, PaymentMethods methods) {
}
