package danila.mediasoft.test.warehouse.converter.orderproduct;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO;
import danila.mediasoft.test.warehouse.entities.OrderProduct;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderProductToOrderProductDTOConverter implements Converter<OrderProduct, OrderProductDTO> {
    @Override
    public OrderProductDTO convert(OrderProduct orderProduct) {
        return OrderProductDTO.builder()
                .name(orderProduct.getProduct().getName())
                .price(orderProduct.getPrice())
                .productId(orderProduct.getId().getProductId())
                .quantity(orderProduct.getQuantity())
                .build();
    }
}
