package danila.mediasoft.test.warehouse.dto.product;

import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProductDTO {
    UUID id;
    String name;
    String article;
    List<ProductTypeDTO> types;
    Long price;
    Integer quantity;

}
