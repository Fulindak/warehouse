package danila.mediasoft.test.warehouse.dto.product;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductCreatedResponseDTO {
    private UUID id;
}
