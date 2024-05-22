package danila.mediasoft.test.warehouse.dto.order;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Builder
public record OrderResponse(UUID orderId, Set<OrderProductResponse> products, BigDecimal price) {
}
