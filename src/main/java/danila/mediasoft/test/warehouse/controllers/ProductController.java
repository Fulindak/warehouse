package danila.mediasoft.test.warehouse.controllers;

import danila.mediasoft.test.warehouse.dto.product.CreateProductDTO;
import danila.mediasoft.test.warehouse.dto.product.GetProductDTO;
import danila.mediasoft.test.warehouse.dto.product.UpdateProductDto;
import danila.mediasoft.test.warehouse.dto.product.UpdateQuantityDTO;
import danila.mediasoft.test.warehouse.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
@Slf4j
public class ProductController {

    private final ProductService productService;
    @GetMapping("/")
    public  ResponseEntity<List<GetProductDTO>> getProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size
    ) {
        return new ResponseEntity<>(productService.getProducts(PageRequest.of(page, size)),
                    HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public  ResponseEntity<GetProductDTO> getProductById(@PathVariable UUID productId) {
        return new ResponseEntity<>(productService.getProductById(productId),
                            HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseStatus> createProduct(@Validated @RequestBody CreateProductDTO createProductDTO) {
        log.info("Receive createProductDTO:" + createProductDTO);
        productService.createProduct(createProductDTO);
        log.info("Product create successfully");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/type")
    public ResponseEntity<GetProductDTO> deleteType(@PathVariable UUID productId, @RequestParam Long typeId) {
        return new ResponseEntity<>(productService.deleteTypeFromId(productId, typeId), HttpStatus.OK);
    }

    @PostMapping("/{productId}/type/{typeId}")
    public ResponseEntity<GetProductDTO> addType(@PathVariable UUID productId, @PathVariable Long typeId) {
        return new ResponseEntity<>(productService.addTypeFromId(productId, typeId), HttpStatus.OK);
    }

    @PutMapping(path = "/{productId}")
    private ResponseEntity<GetProductDTO> updateProduct(@Validated @RequestBody UpdateProductDto productDTO, @PathVariable UUID productId) {
        return new ResponseEntity<>(productService.updateProduct(productId, productDTO) ,HttpStatus.OK);
    }

    @PostMapping("/{productId}/quantity")
    public ResponseEntity<ResponseStatus> updatePrice(@PathVariable UUID productId,@Validated @RequestBody UpdateQuantityDTO quantityDTO) {
        productService.updateQuantity(productId, quantityDTO.getQuantity());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
