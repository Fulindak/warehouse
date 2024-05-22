package danila.mediasoft.test.warehouse.dto.orderproduct;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderProductRequest(
        @Schema(description = "Product id", example = "98a125c3-a5e8-4e1d-81eb-7e57bff69e47")
        @NotNull(message = "Product id must be not null")
        UUID id,
        @Schema(description = "Product quantity", example = "1")
        @NotNull(message = "Quantity must be not null")
        Long quantity) {
}
