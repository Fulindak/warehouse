package danila.mediasoft.test.warehouse.dto.product;

import danila.mediasoft.test.warehouse.dto.validation.Marker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Schema(description = "Request for create product")
public class CreateProductDTO {
    @Schema(description = "Product  name", example = "String_2342")
    @NotNull(message = "Product name must be not null", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @Length(min = 1, max = 256, message = "Product name length must be between 1 and 256 characters", groups = {Marker.OnUpdate.class, Marker.OnCreate.class})
    String name;

    @Schema(description = "Product  article", example = "123874")
    @NotNull(message = "Product article must be not null", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @Length(min = 6, max = 12, message = "Product article length must be between 6 and 12 characters", groups = {Marker.OnUpdate.class, Marker.OnCreate.class})
    String article;
    @Schema(description = "Product  types")
    List<Long> types;

    @Schema(description = "Product  quantity in kopecks", example = "25")
    @NotNull(message = "Product quantity must be not null", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    Integer quantity;

    @Schema(description = "Product  article", example = "123874")
    @NotNull(message = "Product article must be not null", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    Long price;
}
