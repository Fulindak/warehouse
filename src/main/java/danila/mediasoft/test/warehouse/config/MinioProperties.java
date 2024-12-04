package danila.mediasoft.test.warehouse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public record MinioProperties(String url, String bucket, String accessKey,  String secretKey) {
}
