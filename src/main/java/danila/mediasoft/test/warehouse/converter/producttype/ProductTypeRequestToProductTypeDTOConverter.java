package danila.mediasoft.test.warehouse.converter.producttype;

import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeDTO;
import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeRequestToProductTypeDTOConverter implements Converter<ProductTypeRequest, ProductTypeDTO> {

    @Override
    public ProductTypeDTO convert(ProductTypeRequest productTypeRequest) {
        return ProductTypeDTO.builder()
                .id(productTypeRequest.getId())
                .name(productTypeRequest.getName())
                .build();
    }
}
