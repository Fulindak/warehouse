package danila.mediasoft.test.warehouse.mappers;

import danila.mediasoft.test.warehouse.dto.product.CreateProductDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductResponse;
import danila.mediasoft.test.warehouse.entities.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;
    public ProductMapper() {
        this.modelMapper = new ModelMapper();

        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
    }


    public Product toEntity(CreateProductDTO createProductDTO){
        return modelMapper.map(createProductDTO, Product.class);
    }

    public ProductResponse toGetProductDTO(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }
}
