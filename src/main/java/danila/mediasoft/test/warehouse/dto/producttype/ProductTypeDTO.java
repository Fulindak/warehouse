package danila.mediasoft.test.warehouse.dto.producttype;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductTypeDTO {
    private Long id;
    private String name;
}
