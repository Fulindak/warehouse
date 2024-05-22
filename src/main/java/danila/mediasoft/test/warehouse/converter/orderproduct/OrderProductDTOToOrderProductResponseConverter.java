package danila.mediasoft.test.warehouse.converter.orderproduct;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderProductDTOToOrderProductResponseConverter implements Converter<OrderProductDTO, OrderProductResponse> {
    @Override
    public OrderProductResponse convert(OrderProductDTO orderProductDTO) {
        return OrderProductResponse.builder()
                .productId(orderProductDTO.productId())
                .name(orderProductDTO.name())
                .price(orderProductDTO.price())
                .quantity(orderProductDTO.quantity())
                .build();
    }
}
