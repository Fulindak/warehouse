package danila.mediasoft.test.warehouse.config;

import danila.mediasoft.test.warehouse.config.model.ContractMethods;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest.contract-service")
public record ContractProperties(String host, ContractMethods methods) {
}
