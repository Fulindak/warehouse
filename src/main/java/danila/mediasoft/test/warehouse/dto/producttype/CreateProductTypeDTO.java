package danila.mediasoft.test.warehouse.dto.producttype;

import danila.mediasoft.test.warehouse.dto.validation.Marker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "Request for create product type")
public class CreateProductTypeDTO {
    @Schema(description = "Product Type name", example = "Gloves")
    @NotNull(message = "Type must be not null", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    @Length(min = 1, max = 256, message = "Type name length must be between 1 and 256 characters", groups = {Marker.OnUpdate.class, Marker.OnCreate.class})
    private String name;
}
