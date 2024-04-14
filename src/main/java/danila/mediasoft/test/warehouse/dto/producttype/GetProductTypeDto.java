package danila.mediasoft.test.warehouse.dto.producttype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response product type")
public class GetProductTypeDto {

    @Schema(description = "Product type id", example = "1234567890987654321")
    private Long id;

    @Schema(description = "Product type name", example = "Овощь")
    private String name;

    public GetProductTypeDto setId(Long id) {
        this.id = id;
        return this;
    }

    public GetProductTypeDto setName(String name) {
        this.name = name;
        return this;
    }
}
