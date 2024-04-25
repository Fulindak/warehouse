package danila.mediasoft.test.warehouse.motherObject;

import danila.mediasoft.test.warehouse.entities.ProductType;

public class ProductTypeBuilder {
    public static final Long DEFAULT_ID = 1L;

    public static final String DEFAULT_NAME = "Meat";

    private Long id = DEFAULT_ID;

    private String name = DEFAULT_NAME;

    private ProductTypeBuilder() {

    }

    public static ProductTypeBuilder aProductType() {
        return new ProductTypeBuilder();
    }
    public static ProductTypeBuilder aDefaultProductType() {
        return ProductTypeBuilder.aProductType().but();
    }
    public ProductTypeBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProductTypeBuilder withName(String name) {
        this.name = name;
        return this;
    }


    public ProductType build() {
        return new ProductType(id, name);
    }

    public ProductTypeBuilder but() {
        return ProductTypeBuilder
                .aProductType()
                .withId(id)
                .withName(name);
    }
}
