package danila.mediasoft.test.warehouse.util;

import danila.mediasoft.test.warehouse.config.MinioProperties;
import danila.mediasoft.test.warehouse.exceptions.UploadException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MinioUtil {
    private final MinioClient minioClient;
    private final MinioProperties properties;

    public UUID uploadFile(MultipartFile file) {
        UUID fileId = UUID.randomUUID();
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(properties.bucket())
                    .object(fileId.toString())
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
        } catch (Exception e) {
            throw new UploadException("Error: Upload file");
        }
        return fileId;
    }

    @SneakyThrows
    public byte[] loadFile(String fileId) {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(properties.bucket())
                .object(fileId)
                .build()).readAllBytes();
    }
}
