package danila.mediasoft.test.warehouse.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MinioConfig {
    private final MinioProperties properties;

    @Bean
    @SneakyThrows
    public MinioClient minioClient() {
        MinioClient client = MinioClient.builder()
                .endpoint(properties.url())
                .credentials(properties.accessKey(), properties.secretKey())
                .build();
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(properties.bucket()).build())) {
            client.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(properties.bucket())
                            .build()
            );
        }
        return client;
    }
}
