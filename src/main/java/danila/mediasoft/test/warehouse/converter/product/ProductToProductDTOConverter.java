package danila.mediasoft.test.warehouse.converter.product;

import danila.mediasoft.test.warehouse.converter.producttype.ProductTypeToProductTypeDTOConverter;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductToProductDTOConverter implements Converter<Product, ProductDTO> {

    private final ProductTypeToProductTypeDTOConverter productTypeDTOConverter;

    @Override
    public ProductDTO convert(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .price(product.getPrice())
                .types(product.getProductTypes()
                        .stream()
                        .map(productTypeDTOConverter::convert).toList())
                .name(product.getName())
                .quantity(product.getQuantity())
                .article(product.getArticle())
                .createAt(product.getCreateAt())
                .updateAt(product.getUpdateAt())
                .build();
    }
}
