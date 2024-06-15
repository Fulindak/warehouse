package danila.mediasoft.test.warehouse.services.order;

import danila.mediasoft.test.warehouse.dto.customer.CustomerInfo;
import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.OrderDTO;
import danila.mediasoft.test.warehouse.dto.order.OrderInfo;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.entities.Customer;
import danila.mediasoft.test.warehouse.entities.Order;
import danila.mediasoft.test.warehouse.entities.OrderProduct;
import danila.mediasoft.test.warehouse.entities.OrderProductId;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import danila.mediasoft.test.warehouse.exceptions.ForbiddenException;
import danila.mediasoft.test.warehouse.exceptions.InvalidStatusException;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotAvailableException;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotFoundException;
import danila.mediasoft.test.warehouse.repositories.CustomerRepository;
import danila.mediasoft.test.warehouse.repositories.OrderProductRepository;
import danila.mediasoft.test.warehouse.repositories.OrderRepository;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import danila.mediasoft.test.warehouse.services.ProductService;
import danila.mediasoft.test.warehouse.services.account.AccountService;
import danila.mediasoft.test.warehouse.services.crm.CrmService;
import danila.mediasoft.test.warehouse.services.customer.provider.CustomerProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CustomerProvider customerProvider;
    private final ConversionService conversionService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final AccountService accountService;
    private final CrmService crmService;

    @Override
    @Transactional
    public UUID create(CreateOrderRequest orderRequest, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer by " + customerProvider.getId() + " not found"));
        Map<UUID, Product> products = new HashMap<>();
        productRepository.findAllById(orderRequest.products()
                        .stream().map(OrderProductRequest::id).toList())
                .forEach(product -> products.put(product.getId(), product));
        if (orderRequest.products().size() != products.size()) {
            throw new ResourceNotFoundException("All products not founds");
        }
        Order order = Order.builder()
                .deliveryAddress(orderRequest.deliveryAddress())
                .customer(customer)
                .orderStatus(OrderStatus.CREATED)
                .build();
        Set<OrderProduct> orderProducts = new HashSet<>();
        products.forEach((productId, product) -> {
            if (product.getIsAvailable().equals(Boolean.FALSE)) {
                throw new ResourceNotAvailableException("Product by id: " + product.getId() + " not available");
            }
            orderProducts.add(
                    OrderProduct.builder()
                            .id(OrderProductId.builder()
                                    .orderId(order.getId())
                                    .productId(productId)
                                    .build())
                            .product(product)
                            .order(order)
                            .price(product.getPrice())
                            .quantity(checkQuantity(product,
                                    orderRequest.products()
                                            .stream()
                                            .filter(orderProductRequest ->
                                                    orderProductRequest.id().equals(productId))
                                            .findFirst()
                                            .get()
                                            .quantity()))
                            .build()
            );

        });
        order.setProducts(orderProducts);
        return orderRepository.save(order).getId();
    }

    @Override
    @Transactional
    public void update(Set<OrderProductRequest> products, UUID orderId, Long customerId) {
        Order order = getVerifiedOrder(orderId, customerId);
        updateOrderProduct(products, order);
        orderRepository.save(order);
    }

    private void updateOrderProduct(Set<OrderProductRequest> orderProductRequests, Order order) {
        Map<UUID, Product> products = new HashMap<>();
        productRepository.findAllById(orderProductRequests
                        .stream().map(OrderProductRequest::id).toList())
                .forEach(product -> products.put(product.getId(), product));
        Map<UUID, OrderProduct> orderProductMap = order.getProducts().stream()
                .collect(Collectors
                        .toMap(it -> it.getId().getProductId(), Function.identity()));
        for (OrderProductRequest orderProductRequest : orderProductRequests) {
            UUID productId = orderProductRequest.id();
            Product product = products.get(orderProductRequest.id());
            if (product == null) {
                throw new ResourceNotFoundException("Product by id: " + productId + " not found");
            }
            if (product.getIsAvailable().equals(Boolean.FALSE)) {
                throw new ResourceNotAvailableException("Product by id: " + product.getId() + " not available");
            }
            Optional.ofNullable(orderProductMap.get(productId))
                    .ifPresentOrElse(
                            it -> {
                                it.setQuantity(it.getQuantity() +
                                        checkQuantity(it.getProduct(), orderProductRequest.quantity()));
                                it.setPrice(it.getProduct().getPrice());
                            },
                            () -> {
                                OrderProduct orderProduct = OrderProduct.builder()
                                        .product(products.get(productId))
                                        .id(OrderProductId.builder()
                                                .orderId(order.getId())
                                                .productId(productId)
                                                .build())
                                        .quantity(0L)
                                        .order(order)
                                        .build();
                                orderProduct.setQuantity(orderProduct.getQuantity() +
                                        checkQuantity(orderProduct.getProduct(), orderProductRequest.quantity()));
                                orderProduct.setPrice(orderProduct.getProduct().getPrice());
                                order.getProducts().add(orderProduct);
                            }
                    );
        }

    }

    @Override
    public OrderDTO get(UUID orderId, Long customerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order by id: " + orderId + " not found"));
        if (!order.getCustomer().getId().equals(customerId)) {
            throw new ForbiddenException("Customer id != Customer in order");
        }
        Set<OrderProductDTO> products = orderProductRepository
                .getProjectionsByOrderIdAndCustomerId(orderId, customerProvider.getId());
        return OrderDTO.builder()
                .orderId(orderId)
                .products(products)
                .price(products.stream()
                        .map(product ->
                                product.price()
                                        .multiply(BigDecimal.valueOf(product.quantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }

    @Override
    public void delete(UUID orderId, Long customerId) {
        Order order = getVerifiedOrder(orderId, customerId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public OrderDTO updateStatus(UpdateStatusRequest updateStatusRequest, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order by id: " + orderId + " not found"));
        if (!order.getOrderStatus().equals(OrderStatus.CREATED)) {
            throw new InvalidStatusException("Order cannot be changed");
        }
        order.setOrderStatus(updateStatusRequest.status());
        return conversionService.convert(orderRepository.save(order), OrderDTO.class);
    }


    @Override
    public Map<UUID, List<OrderInfo>> getOrdersInfoGroupByProductId() {
        List<Order> orders = orderRepository.findOrdersWithStatuses(List.of(OrderStatus.CREATED, OrderStatus.CONFIRMED));
        if (orders.isEmpty()) {
            return new HashMap<>();
        }
        List<String> customersLogin = orders.stream().map(order -> order.getCustomer().getLogin()).toList();
        CompletableFuture<Map<String, String>> accountsFuture = accountService.getAsyncAccounts(customersLogin);
        CompletableFuture<Map<String, String>> innFuture = crmService.getAsyncInn(customersLogin);
        return orders.stream().flatMap(order -> order.getProducts().stream()).map(prod -> Pair.of(prod, prod.getOrder()))
                .collect(Collectors.groupingBy(it -> it.getLeft().getId().getProductId(),
                        Collectors.mapping(it -> {
                            Order order = it.getRight();
                            Customer customer = order.getCustomer();
                            return OrderInfo.builder()
                                    .id(order.getId())
                                    .customer(CustomerInfo.builder()
                                            .id(customer.getId())
                                            .accountNumber(Optional.ofNullable(accountsFuture.join())
                                                    .orElseThrow(() -> new ResourceNotAvailableException("Account service is unavailable"))
                                                    .get(customer.getLogin()))
                                            .email(customer.getEmail())
                                            .inn(Optional.ofNullable(innFuture.join())
                                                    .orElseThrow(() -> new ResourceNotFoundException("CRM service is unavailable"))
                                                    .get(customer.getLogin()))
                                            .build())
                                    .status(order.getOrderStatus())
                                    .deliverAddress(order.getDeliveryAddress())
                                    .quantity(it.getLeft().getQuantity())
                                    .build();
                        }, Collectors.toList())));
    }

    private Order getVerifiedOrder(UUID id, Long customerId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order by id: " + id + " not found"));
        if (!order.getCustomer().getId().equals(customerId)) {
            throw new ForbiddenException("Customer id != Customer in order");
        }
        if (!order.getOrderStatus().equals(OrderStatus.CREATED)) {
            throw new InvalidStatusException("Order cannot be changed");
        }
        return order;
    }

    private Long checkQuantity(Product product, Long quantity) {
        productService.updateQuantity(product.getId(), product.getQuantity() - quantity);
        return quantity;
    }
}
