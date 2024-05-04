package danila.mediasoft.test.warehouse.services;

import danila.mediasoft.test.warehouse.converter.product.ProductToProductDTOConverter;
import danila.mediasoft.test.warehouse.converter.producttype.ProductTypeToProductTypeDTOConverter;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.motherObject.ProductBuilder;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    ProductService underTest;
    @Mock
    private ConversionService conversionServiceMock;
    PageRequest page = PageRequest.of(0, 2);

    @BeforeEach
    void setUp() {
        Converter<Product, ProductDTO> productToProductDTOConverter = new ProductToProductDTOConverter(new ProductTypeToProductTypeDTOConverter());
        when(conversionServiceMock.convert(any(), eq(ProductDTO.class)))
                .thenAnswer(invocation -> {
                    Object argument = invocation.getArgument(0);
                    return productToProductDTOConverter.convert((Product) argument);
                });
    }

    @Test
    public void givenProductEntity_whenFindAll_thenReturnExpectedProductDtoList() {
        final String expectedArticle1 = "expected first article";
        final String expectedArticle2 = "expected second article";
        final BigDecimal expectedPrice1 = BigDecimal.valueOf(51.1);
        final BigDecimal expectedPrice2 = BigDecimal.valueOf(50);

        when(productRepositoryMock.findAll(any(Pageable.class))).thenReturn(
                new PageImpl<>(
                        List.of(
                                ProductBuilder.aDefaultProduct()
                                        .withArticle(expectedArticle1)
                                        .withPrice(expectedPrice1)
                                        .build(),
                                ProductBuilder.aDefaultProduct().
                                        withArticle(expectedArticle2)
                                        .withPrice(expectedPrice2)
                                        .build()
                        )
                )
        );

        final List<ProductDTO> actual = underTest.getProducts(page);

        Assertions.assertThat(actual)
                .anySatisfy(productDTO -> {
                    assertEquals(expectedArticle1, productDTO.getArticle());
                    assertEquals(expectedPrice1, productDTO.getPrice());
                })
                .anySatisfy(productDTO -> {
                    assertEquals(expectedArticle2, productDTO.getArticle());
                    assertEquals(expectedPrice2, productDTO.getPrice());
                })
                .hasSize(page.getPageSize());
        verify(productRepositoryMock, times(1)).findAll(any(Pageable.class));
    }
}