package danila.mediasoft.test.warehouse.controllers;

import danila.mediasoft.test.warehouse.dto.producttype.CreateProductTypeDTO;
import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeDTO;
import danila.mediasoft.test.warehouse.dto.producttype.ProductTypeResponse;
import danila.mediasoft.test.warehouse.services.ProductTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("product-types")
@Slf4j
public class ProductTypeController {
    private final ProductTypeService productTypeService;
    private final ConversionService conversionService;

    @GetMapping(value = "/")
    public List<ProductTypeResponse> getTypes() {
        List<ProductTypeDTO> productTypes = productTypeService.getAllTypes();
        return productTypes.stream()
                .map(productTypeDTO ->
                        conversionService.convert(productTypeDTO, ProductTypeResponse.class))
                .toList();
    }

    @PostMapping(value = "/")
    public ResponseEntity<ResponseStatus> createType(@Validated @RequestBody CreateProductTypeDTO createProductTypeDTO) {
        log.info("Receive createProductTypeDTO:" + createProductTypeDTO);
        productTypeService.createProductType(createProductTypeDTO);
        log.info("Product type create successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
