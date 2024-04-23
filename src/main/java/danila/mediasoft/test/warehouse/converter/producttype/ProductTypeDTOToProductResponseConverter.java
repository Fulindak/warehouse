package danila.mediasoft.test.warehouse.converter.producttype;

import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeDTO;
import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeDTOToProductResponseConverter implements Converter<ProductTypeDTO, ProductTypeResponse> {
    @Override
    public ProductTypeResponse convert(ProductTypeDTO productType)
    {
        return ProductTypeResponse.builder()
                .id(productType.getId())
                .name(productType.getName())
                .build();
    }
}
