package danila.mediasoft.test.warehouse.converter.product;

import danila.mediasoft.test.warehouse.converter.producttype.ProductTypeRequestToProductTypeDTOConverter;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.dto.product.UpdateProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProductDTOToProductDTOConverter implements Converter<UpdateProductDTO, ProductDTO> {

    private final ProductTypeRequestToProductTypeDTOConverter productTypeDTOConverter;
    @Override
    public ProductDTO convert(UpdateProductDTO updateProductDTO) {
        return ProductDTO.builder()
                .name(updateProductDTO.getName())
                .article(updateProductDTO.getArticle())
                .types(updateProductDTO.getTypes().stream()
                        .map(productTypeDTOConverter::convert).toList())
                .quantity(updateProductDTO.getQuantity())
                .price(updateProductDTO.getPrice())
                .build();
    }
}
