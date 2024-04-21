package danila.mediasoft.test.warehouse.services;

import danila.mediasoft.test.warehouse.dto.product.CreateProductDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.dto.product.UpdateProductDTO;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotFoundException;
import danila.mediasoft.test.warehouse.exceptions.ValueAlreadyExistsException;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeService productTypeService;
    private final ConversionService conversionService;

    public void createProduct(CreateProductDTO createProductDTO) {
        try {
            Product product = new Product();
            product.setPrice(createProductDTO.getPrice())
                    .setQuantity(createProductDTO.getQuantity())
                    .setArticle(createProductDTO.getArticle())
                    .setName(createProductDTO.getName());//productMapper.toEntity(createProductDTO);
            createProductDTO.getTypes().stream()
                    .map(productTypeService::getById)
                    .forEach(product::addType);
            log.info("Save product : " + product);
            productRepository.save(product);
        }   catch (DataIntegrityViolationException e) {
            log.info("Product  exist");
            throw new ValueAlreadyExistsException("Product by article '" + createProductDTO.getArticle() + "' already exist");
        }
    }

    public List<ProductDTO> getProducts(PageRequest pageRequest) {
        List<Product> products = productRepository.findAll(pageRequest).getContent();
        return products.stream().map(product -> conversionService.convert(product, ProductDTO.class)).toList();
    }

    @Transactional
    public void updateQuantity(UUID productId, Integer newQuantity) {
        try {
            log.info("Start update product by id :" + productId);
            productRepository.updateQuantity(productId, newQuantity);
            log.info("Update  success");
        }   catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Product not found");
        }
    }

    @Transactional
    public void updatePrice(UUID productId, Long newPrice) {
        try {
            log.info("Start update product by id :" + productId);
            productRepository.updatePrice(productId, newPrice);
            log.info("Update  success");
        }   catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Product not found");
        }
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
        return productRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
    public ProductDTO getProductById(UUID uuid) {
        return getProductDTO(getProductAndTypes(uuid));
    }

    @Transactional
    public ProductDTO updateProduct(UUID productId, UpdateProductDTO updateProductDto) {
        Product product = getProductAndTypes(productId);
        product.setProductTypes(new ArrayList<>());
        product.setPrice(updateProductDto.getPrice())
                .setQuantity(updateProductDto.getQuantity())
                .setArticle(updateProductDto.getArticle())
                .setName(updateProductDto.getName());//productMapper.toEntity(createProductDTO);
        updateProductDto.getTypes().stream()
                .map(productTypeService::getById)
                .forEach(product::addType);
        return getProductDTO(productRepository.save(product));
    }

}

