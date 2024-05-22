package danila.mediasoft.test.warehouse.dto.order;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Builder
public record OrderDTO(UUID orderId, Set<OrderProductDTO> products, BigDecimal price) {
}
