package danila.mediasoft.test.warehouse.dto.product;

import danila.mediasoft.test.warehouse.dto.validation.Marker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for update quantity product")
public class UpdateQuantityDTO {
    @Schema(description = "Product  quantity in kopecks", example = "25")
    @NotNull(message = "Product quantity must be not null", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private Long quantity;
}
