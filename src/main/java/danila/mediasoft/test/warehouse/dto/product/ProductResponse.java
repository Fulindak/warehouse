package danila.mediasoft.test.warehouse.dto.product;

import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
@Builder
public class ProductResponse {
    UUID id;
    String name;
    String article;
    List<ProductTypeResponse> types;
    Long price;
    Integer quantity;
}
