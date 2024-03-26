package danila.mediasoft.test.warehouse.dto.product;

import danila.mediasoft.test.warehouse.entities.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
@Data
@Schema(description = "Response product")
public class GetProductDTO {
    @Schema(description = "Product id", example = "1234567890987654321")
    UUID id;
    @Schema(description = "Product name", example = "Adidas")
    String name;
    @Schema(description = "Product article", example = "123456")
    String article;
    @Schema(description = "Product types")
    Set<ProductType> types;
    @Schema(description = "Product price", example = "1234567890987654321")
    Long price;
    @Schema(description = "Product quantity", example = "12345")
    Integer quantity;

    public GetProductDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public GetProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public GetProductDTO setArticle(String article) {
        this.article = article;
        return this;
    }

    public GetProductDTO setPrice(Long price) {
        this.price = price;
        return this;
    }

    public GetProductDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public GetProductDTO setTypes(Set<ProductType> types) {
        this.types = types;
        return this;
    }
}
