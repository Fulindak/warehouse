package danila.mediasoft.test.warehouse.dto.order;

import danila.mediasoft.test.warehouse.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateStatusRequest(
        @Schema(description = "Order status", example = "DONE")
        @NotNull(message = "Order status must be not null")
        OrderStatus status) {
}
