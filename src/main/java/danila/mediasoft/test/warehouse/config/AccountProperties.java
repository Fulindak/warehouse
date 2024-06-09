package danila.mediasoft.test.warehouse.config;

import danila.mediasoft.test.warehouse.config.model.AccountMethods;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest.account-service")
public record AccountProperties(String host, AccountMethods methods, String filePath) {
}
