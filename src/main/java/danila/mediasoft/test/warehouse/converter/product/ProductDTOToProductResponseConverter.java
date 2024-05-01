package danila.mediasoft.test.warehouse.converter.product;

import danila.mediasoft.test.warehouse.bean.CurrencyProvider;
import danila.mediasoft.test.warehouse.converter.producttype.ProductTypeDTOToProductResponseConverter;
import danila.mediasoft.test.warehouse.dto.product.ProductDTO;
import danila.mediasoft.test.warehouse.dto.product.ProductResponse;
import danila.mediasoft.test.warehouse.enums.CurrencyType;
import danila.mediasoft.test.warehouse.services.currency.CurrencyServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductDTOToProductResponseConverter implements Converter<ProductDTO, ProductResponse> {

    private final ProductTypeDTOToProductResponseConverter productResponseConverter;
    private final CurrencyProvider currencyProvider;
    private final CurrencyServiceClient currencyServiceClient;
    @Override
    public ProductResponse convert(ProductDTO productDTO) {
        BigDecimal price = productDTO.getPrice();
        CurrencyType currencyType = currencyProvider.getCurrency();
        price =  currencyType != CurrencyType.RUB ?
                price.multiply(currencyServiceClient.getExchangeRate(currencyType)) :
                price;
        return ProductResponse.builder()
                .id(productDTO.getId())
                .price(price)
                .types(productDTO.getTypes()
                        .stream()
                        .map(productResponseConverter::convert).toList())
                .name(productDTO.getName())
                .quantity(productDTO.getQuantity())
                .article(productDTO.getArticle())
                .createAt(productDTO.getCreateAt())
                .updateAt(productDTO.getUpdateAt())
                .currencyType(currencyType)
                .build();
    }
}
