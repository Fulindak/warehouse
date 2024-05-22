package danila.mediasoft.test.warehouse.dto.order;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateOrderResponse(UUID id) {
}
