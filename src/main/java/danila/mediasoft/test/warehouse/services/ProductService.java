package danila.mediasoft.test.warehouse.services;

import danila.mediasoft.test.warehouse.dto.product.CreateProductDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.entities.ProductType;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotFoundException;
import danila.mediasoft.test.warehouse.exceptions.ValueAlreadyExistsException;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import danila.mediasoft.test.warehouse.services.search.ProductSpecification;
import danila.mediasoft.test.warehouse.services.search.creteria.Criteria;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    private final ProductRepository productRepository;
    private final ProductTypeService productTypeService;
    private final ConversionService conversionService;

    public void createProduct(CreateProductDTO productDTO) {
        if (productRepository.findByArticle(productDTO.getArticle()).isPresent()) {
            log.info("Product with article: " + productDTO.getArticle() + " exist");
            throw new ValueAlreadyExistsException("Product by article '" + productDTO.getArticle() + "' already exist");
        }
        Product product = Product.builder()
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .article(productDTO.getArticle())
                .name(productDTO.getName())
                .productTypes(new ArrayList<>())
                .build();
        for (Long i : productDTO.getTypes()) {
            ProductType productType = productTypeService.getById(i);
            product.addType(productType);
        }
        productRepository.save(product);
        log.info("Save product : " + product);
    }

    public List<ProductDTO> getProducts(PageRequest pageRequest) {
        List<Product> products = productRepository.findAll(pageRequest).getContent();
        return products.stream().map(product -> conversionService.convert(product, ProductDTO.class)).toList();
    }

    @Transactional
    public void updateQuantity(UUID productId, Long newQuantity) {
        if (productRepository.findById(productId).isEmpty()) {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
        }

        log.info("Start update product by id :" + productId);
        productRepository.updateQuantity(productId, newQuantity);
        log.info("Update  success");

    }

    @Transactional
    public void updatePrice(UUID productId, Long newPrice) {
        if (productRepository.findById(productId).isEmpty()) {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
        }

        log.info("Start update product by id :" + productId);
        productRepository.updatePrice(productId, newPrice);
        log.info("Update  success");
    }

    public ProductDTO addTypeFromId(UUID productId, Long typeId) {
        Product product = getProductAndTypes(productId);
        product.addType(productTypeService.getById(typeId));
        return getProductDTO(productRepository.save(product));
    }

    private ProductDTO getProductDTO(Product product) {
        return conversionService.convert(product, ProductDTO.class);
    }

    public ProductDTO deleteTypeFromId(UUID productId, Long typeId) {
        Product product = getProductAndTypes(productId);
        product.removeType(productTypeService.getById(typeId));
        return getProductDTO(productRepository.save(product));
    }

    public Product getProductAndTypes(UUID uuid) {
        return productRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }

    public ProductDTO getProductById(UUID uuid) {
        return getProductDTO(getProductAndTypes(uuid));
    }

    @Transactional
    public ProductDTO updateProduct(UUID productId, ProductDTO productDTO) {
        Product product = getProductAndTypes(productId);
        product.setProductTypes(new ArrayList<>());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setArticle(productDTO.getArticle());
        product.setName(productDTO.getName());
        productDTO.getTypes().stream()
                .map(type -> productTypeService.getById(type.getId()))
                .forEach(product::addType);

        return getProductDTO(productRepository.save(product));
    }

    public List<ProductDTO> search(List<Criteria> criteriaList, Pageable pageable) {
        ProductSpecification specification;
        specification = new ProductSpecification(criteriaList);
        List<Product> products = productRepository.findAll(specification, pageable).getContent();
        return products.stream()
                .map(product ->
                        conversionService.convert(product, ProductDTO.class)).toList();
    }

    @Transactional
    public void insertDemoValue(int size) {
        productRepository.deleteAll();
        var products = IntStream.range(0, size)
                .mapToObj(
                        index ->
                                Product.builder()
                                        .price(BigDecimal.valueOf(index + 50))
                                        .article("article_" + index)
                                        .name("product_" + index)
                                        .id(UUID.randomUUID())
                                        .quantity((long) index)
                                        .productTypes(new ArrayList<>())
                                        .build())
                .toList();
        productRepository.saveAll(products);
    }
}

