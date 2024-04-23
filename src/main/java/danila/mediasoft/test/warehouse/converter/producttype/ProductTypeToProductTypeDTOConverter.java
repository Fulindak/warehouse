package danila.mediasoft.test.warehouse.converter.producttype;

import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeDTO;
import danila.mediasoft.test.warehouse.entities.ProductType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeToProductTypeDTOConverter implements Converter<ProductType, ProductTypeDTO> {
    @Override
    public ProductTypeDTO convert(ProductType productType)
    {
        return ProductTypeDTO.builder()
                .id(productType.getId())
                .name(productType.getName())
                .build();
    }
}
