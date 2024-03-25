package danila.mediasoft.test.warehouse.controllers;

import danila.mediasoft.test.warehouse.dto.producttype.CreateProductTypeDTO;
import danila.mediasoft.test.warehouse.dto.producttype.GetProductTypeDTO;
import danila.mediasoft.test.warehouse.entities.ProductType;
import danila.mediasoft.test.warehouse.services.ProductTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product-type")
@Slf4j
public class ProductTypeController {
    private final ProductTypeService productTypeService;

    @GetMapping(value = "/")
    public ResponseEntity<List<ProductType>> getTypes() {
        return new ResponseEntity<>(
                productTypeService.getAllTypes(),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/")
    public ResponseEntity<ResponseStatus> createType(@RequestBody CreateProductTypeDTO createProductTypeDTO) {
        log.info("Receive createProductTypeDTO:" + createProductTypeDTO);
        productTypeService.createProductType(createProductTypeDTO);
        log.info("Product type create successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
