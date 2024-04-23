package danila.mediasoft.test.warehouse.dto.product;

import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProductDTO {
    private UUID id;
    private String name;
    private String article;
    private List<ProductTypeDTO> types;
    private BigDecimal price;
    private Long quantity;
    private LocalDateTime updateAt;
    private LocalDate createAt;
}
