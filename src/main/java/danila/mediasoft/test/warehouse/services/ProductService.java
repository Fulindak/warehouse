package danila.mediasoft.test.warehouse.services;

import danila.mediasoft.test.warehouse.dto.product.CreateProductDTO;
import danila.mediasoft.test.warehouse.dto.product.GetProductDTO;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotFoundException;
import danila.mediasoft.test.warehouse.exceptions.ValueAlreadyExistsException;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeService productTypeService;

    public void createProduct(CreateProductDTO createProductDTO) {
        try {
            Product product = new Product();
            updateProductEntity(createProductDTO, product);
            log.info("Save product : " + product);
            productRepository.save(product);
        }   catch (DataIntegrityViolationException e) {
            log.info("Product  exist");
            throw new ValueAlreadyExistsException("Product by article '" + createProductDTO.getArticle() + "' already exist");
        }
    }

    private void updateProductEntity(CreateProductDTO createProductDTO, Product product) {
        product.setPrice(createProductDTO.getPrice())
                .setQuantity(createProductDTO.getQuantity())
                .setArticle(createProductDTO.getArticle())
                .setName(createProductDTO.getName());//productMapper.toEntity(createProductDTO);
        createProductDTO.getTypes().stream()
                .map(productTypeService::getById)
                .forEach(product::addType);
    }

    public List<GetProductDTO> getProducts(PageRequest pageRequest) {
        List<GetProductDTO> products = new ArrayList<>();
        List<Product> page = productRepository.findAll(pageRequest).getContent();
        for (Product product : page) {
            products.add(getProductDTO(product));
        }
        return products;
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

    public GetProductDTO addTypeFromId(UUID productId,Long typeId) {
        Product product = getProductAndTypes(productId);
        product.addType(productTypeService.getById(typeId));
        return getProductDTO(productRepository.save(product));
    }

    private GetProductDTO getProductDTO(Product product) {
        return new GetProductDTO()
                .setId(product.getId())
                .setName(product.getName())
                .setArticle(product.getArticle())
                .setPrice(product.getPrice())
                .setTypes(product.getProductTypes())
                .setQuantity(product.getQuantity());
    }

    public GetProductDTO deleteTypeFromId(UUID productId,Long typeId) {
        Product product = getProductAndTypes(productId);
        product.removeType(productTypeService.getById(typeId));
        return getProductDTO(productRepository.save(product));
    }

    public Product getProductAndTypes(UUID uuid) {
        return productRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
    public GetProductDTO getProductById(UUID uuid) {
        return getProductDTO(getProductAndTypes(uuid));
    }

    @Transactional
    public GetProductDTO updateProduct(UUID productId, CreateProductDTO createProductDTO) {
        Product product = getProductAndTypes(productId);
        product.setProductTypes(new HashSet<>());
        updateProductEntity(createProductDTO, product);
        return getProductDTO(productRepository.save(product));
    }

}
