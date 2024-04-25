package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.motherObject.ProductBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    private final Integer dbSize = 50;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productTypeRepository.deleteAll();
        productTypeRepository.saveAll(ProductBuilder.DEFAULT_PRODUCT_TYPES);
        productRepository.saveAll(IntStream.range(0, dbSize)
                .mapToObj(
                        index ->
                                ProductBuilder
                                        .aDefaultProduct()
                                        .withPrice(ProductBuilder.DEFAULT_PRICE.add(BigDecimal.valueOf(index)))
                                        .withArticle("article_" + index)
                                        .withName("product_" + index)
                                        .withId(UUID.randomUUID())
                                        .withQuantity((long) index)
                                        .build())
                        .toList()
                );
    }

    @Test
    void shouldFindAll() {
        var products = productRepository.findAll();
        assertThat(products.size()).isEqualTo(dbSize);
    }
}