package danila.mediasoft.test.warehouse.services.orderproduct;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.entities.Order;
import danila.mediasoft.test.warehouse.entities.OrderProduct;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import danila.mediasoft.test.warehouse.repositories.OrderRepository;
import danila.mediasoft.test.warehouse.services.ProductService;
import danila.mediasoft.test.warehouse.services.customer.CustomerService;
import danila.mediasoft.test.warehouse.services.customer.provider.CustomerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final CustomerProvider customerProvider;

    @Override
    @Transactional
    public void createOrder(CreateOrderRequest orderRequest) {
        Set<Product> products = orderRequest.products()
                .stream()
                .map(product -> productService
                        .getProductAndTypes(product.id()))
                .collect(Collectors.toSet());
        Order order = Order.builder()
                .deliveryAddress(orderRequest.deliveryAddress())
                .products(products.stream()
                        .map(product ->
                                OrderProduct.builder()
                                        .product(product)
                                        .price(product.getPrice())
                                        .quantity(product.getQuantity())
                                        .build())
                        .collect(Collectors.toSet()))
                .customer(customerService.findById(customerProvider.getId()))
                .orderStatus(OrderStatus.CREATED)
                .build();
        orderRepository.save(order);
    }
}
