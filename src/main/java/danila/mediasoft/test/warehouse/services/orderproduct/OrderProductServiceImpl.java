package danila.mediasoft.test.warehouse.services.orderproduct;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.entities.Order;
import danila.mediasoft.test.warehouse.entities.OrderProduct;
import danila.mediasoft.test.warehouse.entities.OrderProductId;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.repositories.OrderProductRepository;
import danila.mediasoft.test.warehouse.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {
    private final ProductService productService;
    private final OrderProductRepository orderProductRepository;

    @Override
    public Set<OrderProduct> create(Set<OrderProductRequest> orderProductRequests, Order order) {
        Set<Product> products = orderProductRequests
                .stream()
                .map(product -> productService
                        .getProductAndTypes(product.id()))
                .collect(Collectors.toSet());
        return products.stream()
                .map(product ->
                        OrderProduct.builder()
                                .id(OrderProductId.builder()
                                        .productId(product.getId())
                                        .orderId(order.getId()).build())
                                .product(product)
                                .order(order)
                                .price(product.getPrice())
                                .quantity(checkQuantity(product, orderProductRequests))
                                .build())
                .collect(Collectors.toSet());
    }

    @Override
    public OrderProduct create(OrderProductRequest orderProductRequest, Order order) {
        Product product = productService.getProductAndTypes(orderProductRequest.id());
        return OrderProduct.builder()
                .id(OrderProductId.builder()
                        .productId(product.getId())
                        .orderId(order.getId()).build())
                .order(order)
                .product(product)
                .quantity(checkQuantity(product, orderProductRequest))
                .price(product.getPrice())
                .build();
    }

    @Override
    public Set<OrderProduct> update(Set<OrderProductRequest> orderProductRequests, Order order) {
        return orderProductRequests
                .stream()
                .map(product -> update(product, order)).collect(Collectors.toSet());
    }

    @Override
    public OrderProduct update(OrderProductRequest orderProductRequests, Order order) {
        OrderProduct product = getById(OrderProductId.builder()
                .orderId(order.getId())
                .productId(orderProductRequests.id())
                .build()).orElseGet(() -> create(orderProductRequests, order));
        product.setQuantity(product.getQuantity() + checkQuantity(product.getProduct(), orderProductRequests));
        product.setPrice(product.getProduct().getPrice());
        return product;
    }

    @Override
    public Set<OrderProductDTO> getByOrderId(UUID id) {
        return orderProductRepository.findByOrderId(id);
    }

    @Override
    public Optional<OrderProduct> getById(OrderProductId id) {
        return orderProductRepository.findById(id);
    }

    private Long checkQuantity(Product product, Set<OrderProductRequest> orderProductRequest) {
        Long quantity = orderProductRequest
                .stream()
                .filter(p -> p.id().equals(product.getId()))
                .findFirst().get().quantity();
        productService.updateQuantity(product.getId(), product.getQuantity() - quantity);
        return quantity;
    }

    private Long checkQuantity(Product product, OrderProductRequest orderProductRequest) {
        Long quantity = orderProductRequest.quantity();
        productService.updateQuantity(product.getId(), product.getQuantity() - quantity);
        return quantity;
    }
}
