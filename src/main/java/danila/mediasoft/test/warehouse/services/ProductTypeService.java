package danila.mediasoft.test.warehouse.services;

import danila.mediasoft.test.warehouse.dto.producttype.CreateProductTypeDTO;
import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeDTO;
import danila.mediasoft.test.warehouse.entities.ProductType;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotFoundException;
import danila.mediasoft.test.warehouse.exceptions.ValueAlreadyExistsException;
import danila.mediasoft.test.warehouse.mappers.ProductTypeMapper;
import danila.mediasoft.test.warehouse.repositories.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeMapper productTypeMapper;
    private final ConversionService conversionService;

    public List<ProductTypeDTO> getAllTypes() {
        List<ProductType> types = (List<ProductType>) productTypeRepository.findAll();
        return types.stream()
                .map(productType ->
                        conversionService.convert(productType, ProductTypeDTO.class))
                .toList();
    }

    public ProductType getById(Long id) {
        return productTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product type not found"));
    }

    public void createProductType(CreateProductTypeDTO createProductTypeDTO) {
        try {
            ProductType  productType = productTypeMapper.toEntity(createProductTypeDTO);
            log.info("Save product type: " + productType);
            productTypeRepository.save(productType);
        }   catch (DataIntegrityViolationException e) {
            log.info("Product type exist");
            throw new ValueAlreadyExistsException("Product type by name '" + createProductTypeDTO.getName() + "' already exist");
        }
    }

}
