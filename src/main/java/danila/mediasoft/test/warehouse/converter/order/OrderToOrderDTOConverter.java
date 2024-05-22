package danila.mediasoft.test.warehouse.converter.order;

import danila.mediasoft.test.warehouse.converter.orderproduct.OrderProductToOrderProductDTOConverter;
import danila.mediasoft.test.warehouse.dto.order.OrderDTO;
import danila.mediasoft.test.warehouse.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderToOrderDTOConverter implements Converter<Order, OrderDTO> {
    private final OrderProductToOrderProductDTOConverter orderProductDTOConverter;

    @Override
    public OrderDTO convert(Order order) {
        return OrderDTO.builder()
                .orderId(order.getId())
                .price(order.getProducts().stream()
                        .map(product ->
                                product.getPrice()
                                        .multiply(BigDecimal.valueOf(product.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .products(order.getProducts()
                        .stream()
                        .map(orderProductDTOConverter::convert)
                        .collect(Collectors.toSet()))
                .build();
    }
}
