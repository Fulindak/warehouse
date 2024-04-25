package danila.mediasoft.test.warehouse.motherObject;

import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.entities.ProductType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductBuilder {
    public static final UUID DEFAULT_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

    public static final String DEFAULT_NAME = "Adidas";

    public static final String DEFAULT_ARTICLE = "string";

    public static final Long DEFAULT_QUANTITY = 50L;

    public static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(50);

    public static final List<ProductType> DEFAULT_PRODUCT_TYPES = List.of(ProductTypeBuilder.aDefaultProductType().build());

    public static final LocalDateTime DEFAULT_UPDATE_AT = LocalDateTime.parse("2024-04-25T07:30:46.109");

    public static final LocalDate DEFAULT_CREATE_AT = LocalDate.parse("2024-04-25");

    private UUID id = DEFAULT_ID;

    private String name = DEFAULT_NAME;

    private String article = DEFAULT_ARTICLE;

    private Long quantity = DEFAULT_QUANTITY;

    private BigDecimal price = DEFAULT_PRICE;

    private List<ProductType> productTypes = DEFAULT_PRODUCT_TYPES;

    private LocalDateTime updateAt = DEFAULT_UPDATE_AT;

    private LocalDate createAt = DEFAULT_CREATE_AT;

    private ProductBuilder() {

    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }
    public static ProductBuilder aDefaultProduct() {
        return ProductBuilder.aProduct().but();
    }
    public ProductBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withArticle(String article) {
        this.article = article;
        return this;
    }

    public ProductBuilder withQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
        return this;
    }

    public ProductBuilder withUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public ProductBuilder withCreateAt(LocalDate createAt) {
        this.createAt = createAt;
        return this;
    }


    public Product build() {
        return new Product(id, name, article, quantity, price, productTypes, updateAt, createAt);
    }

    public ProductBuilder but() {
        return ProductBuilder
                .aProduct()
                .withId(id)
                .withName(name)
                .withArticle(article)
                .withQuantity(quantity)
                .withPrice(price)
                .withProductTypes(productTypes)
                .withUpdateAt(updateAt)
                .withCreateAt(createAt);
    }
}

