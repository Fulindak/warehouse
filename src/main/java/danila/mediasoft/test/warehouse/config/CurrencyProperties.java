package danila.mediasoft.test.warehouse.config;

import danila.mediasoft.test.warehouse.config.model.Methods;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest.currency-service")
public record CurrencyProperties(String host, Methods methods, String filePath) {
}
