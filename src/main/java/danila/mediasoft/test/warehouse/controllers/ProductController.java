package danila.mediasoft.test.warehouse.controllers;

import danila.mediasoft.test.warehouse.dto.product.CreateProductDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductCreatedResponseDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductResponse;
import danila.mediasoft.test.warehouse.dto.product.UpdateProductDTO;
import danila.mediasoft.test.warehouse.dto.product.UpdateQuantityDTO;
import danila.mediasoft.test.warehouse.services.ProductService;
import danila.mediasoft.test.warehouse.services.search.creteria.Criteria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ConversionService conversionService;

    @GetMapping("/")
    public List<ProductResponse> getProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size
    ) {
        final List<ProductDTO> products = productService.getProducts(PageRequest.of(page, size));
        return products.stream()
                .map(productDTO -> conversionService.convert(productDTO, ProductResponse.class))
                .toList();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID productId) {
        return new ResponseEntity<>(conversionService.convert(
                productService.getProductById(productId), ProductResponse.class),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ProductCreatedResponseDTO> createProduct(@Validated @RequestBody CreateProductDTO createProductDTO) {
        log.info("Receive createProductDTO:" + createProductDTO);
        return ResponseEntity.ok(ProductCreatedResponseDTO.builder()
                .id(productService
                        .createProduct(createProductDTO))
                .build());
    }

    @DeleteMapping("/{productId}/type")
    public ResponseEntity<ProductResponse> deleteType(@PathVariable UUID productId, @RequestParam Long typeId) {
        return new ResponseEntity<>(conversionService.convert(
                productService.deleteTypeFromId(productId, typeId), ProductResponse.class),
                HttpStatus.OK);
    }

    @PostMapping("/{productId}/type/{typeId}")
    public ResponseEntity<ProductResponse> addType(@PathVariable UUID productId, @PathVariable Long typeId) {
        return new ResponseEntity<>(conversionService.convert(
                productService.addTypeFromId(productId, typeId), ProductResponse.class),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@Validated @RequestBody UpdateProductDTO updateProductDTO, @PathVariable UUID productId) {
        ProductDTO product = productService.updateProduct(productId,
                (Objects.requireNonNull(conversionService.convert(updateProductDTO, ProductDTO.class))));
        return new ResponseEntity<>(conversionService.convert(productService.updateProduct(productId, product), ProductResponse.class),
                HttpStatus.OK);
    }

    @PostMapping("/{productId}/quantity")
    public ResponseEntity<ResponseStatus> updateQuantity(@PathVariable UUID productId, @Validated @RequestBody UpdateQuantityDTO quantityDTO) {
        productService.updateQuantity(productId, quantityDTO.getQuantity());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchByCriteria(Pageable pageable,
                                                                  @Valid @RequestBody List<Criteria> criteriaList) {
        List<ProductDTO> productDTOList = productService.search(criteriaList, pageable);
        return ResponseEntity.ok(productDTOList.stream()
                .map(productDTO ->
                        conversionService.convert(productDTO, ProductResponse.class)).toList());
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID productId) {
        productService.deleteProductById(productId);
    }

    @PostMapping("/{productId}/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID uploadImg(@PathVariable UUID productId, @RequestParam("file") MultipartFile image) {
        return productService.upload(productId, image);
    }

    @PostMapping(value = "/{productId}/download", produces = "application/zip")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StreamingResponseBody> downloadImgsZip(@PathVariable UUID productId) {
        StreamingResponseBody responseBody = outputStream -> {
            productService.downloadImgsZip(productId, outputStream);
        };
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + productId + "_" + LocalDateTime.now() + ".zip\"")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(responseBody);
    }
}
