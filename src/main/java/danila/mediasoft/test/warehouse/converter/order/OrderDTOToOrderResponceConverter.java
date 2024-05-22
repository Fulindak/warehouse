package danila.mediasoft.test.warehouse.converter.order;

import danila.mediasoft.test.warehouse.converter.orderproduct.OrderProductDTOToOrderProductResponseConverter;
import danila.mediasoft.test.warehouse.dto.order.OrderDTO;
import danila.mediasoft.test.warehouse.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderDTOToOrderResponceConverter implements Converter<OrderDTO, OrderResponse> {
    private final OrderProductDTOToOrderProductResponseConverter orderProductDTOConverter;

    @Override
    public OrderResponse convert(OrderDTO orderDTO) {
        return OrderResponse.builder()
                .orderId(orderDTO.orderId())
                .price(orderDTO.price())
                .products(orderDTO.products()
                        .stream()
                        .map(orderProductDTOConverter::convert)
                        .collect(Collectors.toSet()))
                .build();
    }
}
