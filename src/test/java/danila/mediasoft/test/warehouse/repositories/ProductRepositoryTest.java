package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.motherObject.ProductBuilder;
import danila.mediasoft.test.warehouse.services.search.ProductSpecification;
import danila.mediasoft.test.warehouse.services.search.creteria.BigDecimalCriteria;
import danila.mediasoft.test.warehouse.services.search.creteria.Criteria;
import danila.mediasoft.test.warehouse.services.search.creteria.LocalDateCriteria;
import danila.mediasoft.test.warehouse.services.search.creteria.LongCriteria;
import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepositoryUnderTest;
    final String fieldQuantity = "quantity";
    final String fieldCreateAt = "createAt";
    final String fieldPrice = "price";
    private final Integer dbSize = 10;

    PageRequest page = PageRequest.of(0, 5);

    @BeforeEach
    void setUp() {
        productRepositoryUnderTest.deleteAll();
        var products = IntStream.range(0, dbSize)
                .mapToObj(
                        index ->
                                ProductBuilder
                                        .aDefaultProduct()
                                        .withPrice(BigDecimal.valueOf(index + 50))
                                        .withArticle("article_" + index)
                                        .withName("product_" + index)
                                        .withId(UUID.randomUUID())
                                        .withQuantity((long) index)
                                        .withProductTypes(new ArrayList<>())
                                        .build())
                .toList();
        productRepositoryUnderTest.saveAll(products);
    }

    @Test
    void givenProductEntity_whenFindAll_thenReturnExpectedSize() {
        var products = productRepositoryUnderTest.findAll();
        assertThat(products).hasSize(dbSize);
    }

    @Test
    void givenLocalDateCriteria_whenFindAll_thenReturnExpectedLikeProductList() {
        List<Criteria> criteria = List.of(
                LocalDateCriteria.builder()
                        .value(ProductBuilder.DEFAULT_CREATE_AT)
                        .field(fieldCreateAt)
                        .operation(Operation.LIKE)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        var products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(dbSize);
    }

    @Test
    void givenLocalDateCriteria_whenFindAll_thenReturnExpectedEqualProductList() {
        List<Criteria> criteria = List.of(
                LocalDateCriteria.builder()
                        .value(ProductBuilder.DEFAULT_CREATE_AT)
                        .field(fieldCreateAt)
                        .operation(Operation.EQUAL)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        List<Product> products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(dbSize);
    }

    @Test
    void givenLocalDateCriteria_whenFindAll_thenReturnExpectedGreaterProductList() {
        List<Criteria> criteria = List.of(
                LocalDateCriteria.builder()
                        .value(ProductBuilder.DEFAULT_CREATE_AT)
                        .field(fieldCreateAt)
                        .operation(Operation.GRATER_THAN_OR_EQ)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        var products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(dbSize);
    }

    @Test
    void givenLocalDateCriteria_whenFindAll_thenReturnExpectedLessProductList() {
        List<Criteria> criteria = List.of(
                LocalDateCriteria.builder()
                        .value(ProductBuilder.DEFAULT_CREATE_AT)
                        .field(fieldCreateAt)
                        .operation(Operation.LESS_THAN_OR_EQ)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        var products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(dbSize);
    }

    @Test
    void givenLongCriteria_whenFindAll_thenReturnExpectedLikeProductList() {
        List<Criteria> criteria = List.of(
                LongCriteria.builder()
                        .value(1L)
                        .field(fieldQuantity)
                        .operation(Operation.LIKE)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        var products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(1);
    }

    @Test
    void givenLongCriteria_whenFindAll_thenReturnExpectedEqualProductList() {
        List<Criteria> criteria = List.of(
                LongCriteria.builder()
                        .value(25L)
                        .field(fieldQuantity)
                        .operation(Operation.EQUAL)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        var products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(1);
    }

    @Test
    void givenLongCriteria_whenFindAll_thenReturnExpectedGreaterProductList() {
        List<Criteria> criteria = List.of(
                LongCriteria.builder()
                        .value(25L)
                        .field(fieldQuantity)
                        .operation(Operation.EQUAL)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        var products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(1);
    }

    @Test
    void givenLongCriteria_whenFindAll_thenReturnExpectedLessProductList() {
        List<Criteria> criteria = List.of(
                LongCriteria.builder()
                        .value(ProductBuilder.DEFAULT_QUANTITY)
                        .field(fieldQuantity)
                        .operation(Operation.LESS_THAN_OR_EQ)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        var products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(50);
    }

    @Test
    void givenMultipleCriteria_whenFindAll_thenReturnExpectedProductList() {
        List<Criteria> criteria = List.of(
                BigDecimalCriteria.builder()
                        .value(BigDecimal.valueOf(75))
                        .field(fieldPrice)
                        .operation(Operation.LESS_THAN_OR_EQ)
                        .build(),
                BigDecimalCriteria.builder()
                        .value(BigDecimal.valueOf(60))
                        .field(fieldPrice)
                        .operation(Operation.GRATER_THAN_OR_EQ)
                        .build(),
                LocalDateCriteria.builder()
                        .value(ProductBuilder.DEFAULT_CREATE_AT)
                        .field(fieldCreateAt)
                        .operation(Operation.LESS_THAN_OR_EQ)
                        .build()
        );

        ProductSpecification productSpecification = new ProductSpecification(criteria);
        var products = productRepositoryUnderTest.findAll(productSpecification, page).getContent();

        assertThat(products).hasSize(16);
    }
}