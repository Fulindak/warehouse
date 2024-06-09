package danila.mediasoft.test.warehouse.config;

import danila.mediasoft.test.warehouse.config.model.CrmMethods;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest.crm-service")
public record CrmProperties(String host, CrmMethods methods, String filePath) {
}
