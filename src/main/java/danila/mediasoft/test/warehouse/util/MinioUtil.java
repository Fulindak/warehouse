package danila.mediasoft.test.warehouse.util;

import danila.mediasoft.test.warehouse.config.MinioProperties;
import danila.mediasoft.test.warehouse.exceptions.UploadException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MinioUtil {
    private final MinioClient minioClient;
    private final MinioProperties properties;
    public static final String X_META_FILENAME = "x-meta-filename";
    public static final String X_META_EXPANSION = "x-meta-expansion";

    public UUID uploadFile(MultipartFile file) {
        UUID fileId = UUID.randomUUID();
        Map<String, String> metaData = new HashMap<>();
        try {
            metaData.put(X_META_FILENAME, file.getName());
            metaData.put(X_META_EXPANSION, getExtension(file));

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(properties.bucket())
                    .object(fileId.toString())
                    .userMetadata(metaData)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
        } catch (Exception e) {
            throw new UploadException("Error: Upload file : " + e.getMessage());
        }
        return fileId;
    }
    private String getExtension(MultipartFile image) {
        return image.getOriginalFilename()
                .substring(image.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }

    @SneakyThrows
    public byte[] loadFile(String fileId) {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(properties.bucket())
                .object(fileId)
                .build()).readAllBytes();
    }

    @SneakyThrows
    public StatObjectResponse getStatFromFile(String fileId) {
        return minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(properties.bucket())
                        .object(fileId)
                        .build()
        );
    }
}
