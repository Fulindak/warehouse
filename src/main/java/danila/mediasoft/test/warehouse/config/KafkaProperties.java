package danila.mediasoft.test.warehouse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(String url, String groupId) {
}
