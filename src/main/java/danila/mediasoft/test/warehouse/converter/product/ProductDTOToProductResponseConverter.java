package danila.mediasoft.test.warehouse.converter.product;

import danila.mediasoft.test.warehouse.converter.producttype.ProductTypeDTOToProductResponseConverter;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDTOToProductResponseConverter implements Converter<ProductDTO, ProductResponse> {

    private final ProductTypeDTOToProductResponseConverter productResponseConverter;
    @Override
    public ProductResponse convert(ProductDTO productDTO) {
        return ProductResponse.builder()
                .id(productDTO.getId())
                .price(productDTO.getPrice())
                .types(productDTO.getTypes()
                        .stream()
                        .map(productResponseConverter::convert).toList())
                .name(productDTO.getName())
                .quantity(productDTO.getQuantity())
                .article(productDTO.getArticle())
                .createAt(productDTO.getCreateAt())
                .updateAt(productDTO.getUpdateAt())
                .isAvailable(productDTO.getIsAvailable())
                .build();
    }
}
