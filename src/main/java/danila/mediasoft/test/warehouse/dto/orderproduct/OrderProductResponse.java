package danila.mediasoft.test.warehouse.dto.orderproduct;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderProductResponse(UUID productId, String name, Long quantity, BigDecimal price) {
}
