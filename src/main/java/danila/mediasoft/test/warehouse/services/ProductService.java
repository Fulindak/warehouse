package danila.mediasoft.test.warehouse.services;

import danila.mediasoft.test.warehouse.dto.product.CreateProductDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.entities.ProductImg;
import danila.mediasoft.test.warehouse.entities.ProductType;
import danila.mediasoft.test.warehouse.exceptions.InvalidValueException;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotFoundException;
import danila.mediasoft.test.warehouse.exceptions.UploadException;
import danila.mediasoft.test.warehouse.exceptions.ValueAlreadyExistsException;
import danila.mediasoft.test.warehouse.repositories.ProductImgRepository;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import danila.mediasoft.test.warehouse.services.search.ProductSpecification;
import danila.mediasoft.test.warehouse.services.search.creteria.Criteria;
import danila.mediasoft.test.warehouse.util.MinioUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    private final ProductRepository productRepository;
    private final ProductTypeService productTypeService;
    private final ConversionService conversionService;
    private final ProductImgRepository productImgRepository;
    private final MinioUtil minioUtil;

    public UUID createProduct(CreateProductDTO productDTO) {
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
                .isAvailable(true)
                .build();
        for (Long i : productDTO.getTypes()) {
            ProductType productType = productTypeService.getById(i);
            product.addType(productType);
        }
        return productRepository.save(product).getId();
    }

    public List<ProductDTO> getProducts(PageRequest pageRequest) {
        List<Product> products = productRepository.findAll(pageRequest).getContent();
        return products.stream().map(product -> conversionService.convert(product, ProductDTO.class)).toList();
    }

    public void updateQuantity(UUID productId, Long newQuantity) {
        if (productRepository.findById(productId).isEmpty()) {
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
        }
        if (newQuantity < 0) {
            throw new InvalidValueException("Quantity cannot be equal: " + newQuantity);
        }
        log.info("Start update product by id :" + productId);
        productRepository.updateQuantity(productId, newQuantity);
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
        Optional<Product> article = productRepository.findByArticle(productDTO.getArticle());
        if (article.isPresent() && !article.get().getId().equals(productId)) {
            throw new ValueAlreadyExistsException("Product by article: " + article.get().getArticle() + " already exist");
        }
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

    public void deleteProductById(UUID productId) {
        Product product = getProductAndTypes(productId);
        product.setIsAvailable(false);
        productRepository.save(product);
    }

    @Transactional
    public void upload(UUID productId, MultipartFile image) {
        Optional.ofNullable(image)
                .orElseThrow(() -> new UploadException("Image must not be null "));
        productImgRepository.uploadImg(productId, minioUtil.uploadFile(image), getExtension(image));
    }

    private String getExtension(MultipartFile image) {
        return image.getOriginalFilename()
                .substring(image.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }

    @Transactional
    @SneakyThrows
    public void downloadImgs(UUID productId, HttpServletResponse response) {
        Product product = getProductAndTypes(productId);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename(product.getName() + "-" + product.getArticle() + ".zip")
                .build()
                .toString());
        Set<ProductImg> imagesId = product.getImages();
        try (ZipOutputStream zip = new ZipOutputStream(response.getOutputStream())) {
            for (ProductImg img : imagesId) {
                byte[] file = minioUtil.loadFile(img.getImageId().toString());
                if (file == null || file.length == 0) continue;
                zip.putNextEntry(new ZipEntry(img.getImageId().toString() + "." + img.getExpansion()));
                StreamUtils.copy(file, zip);
                zip.flush();
            }
        }
    }
}



