package danila.mediasoft.test.warehouse.mappers;

import danila.mediasoft.test.warehouse.dto.producttype.CreateProductTypeDTO;
import danila.mediasoft.test.warehouse.entities.ProductType;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class ProductTypeMapper {
    private final ModelMapper modelMapper;
    public ProductTypeMapper() {
        this.modelMapper = new ModelMapper();

        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
    }

    public ProductType toEntity(CreateProductTypeDTO createProductTypeDTO){
        return modelMapper.map(createProductTypeDTO, ProductType.class);
    }
}
