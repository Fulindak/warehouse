package danila.mediasoft.test.warehouse.dto.order;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateOrderRequest(
        @Schema(description = "Order delivery address", example = "Ул.Пушкина, д.Калатушкана, кв. 25")
        @NotNull(message = "Order delivery address must be not null")
        String deliveryAddress,
        @NotNull(message = "Products must be not null")
        Set<OrderProductRequest> products) {
}
