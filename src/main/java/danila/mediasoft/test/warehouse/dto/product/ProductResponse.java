package danila.mediasoft.test.warehouse.dto.product;

import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeResponse;
import danila.mediasoft.test.warehouse.enums.CurrencyType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private String article;
    private List<ProductTypeResponse> types;
    private BigDecimal price;
    private Long quantity;
    private LocalDate createAt;
    private LocalDateTime updateAt;
    private CurrencyType currencyType;
    private boolean isAvailable;
}
