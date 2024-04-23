package danila.mediasoft.test.warehouse.converter.producttype;

import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeDTO;
import danila.mediasoft.test.warehouse.entities.ProductType;
import org.springframework.core.convert.converter.Converter;

public class ProductTypeDTOtoProductConverter implements Converter<ProductTypeDTO, ProductType> {
    @Override
    public ProductType convert(ProductTypeDTO productTypeDTO) {
        return ProductType.builder()
                .id(productTypeDTO.getId())
                .name(productTypeDTO.getName())
                .build();
    }
}
