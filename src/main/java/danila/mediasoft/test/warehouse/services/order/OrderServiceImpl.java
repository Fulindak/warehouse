package danila.mediasoft.test.warehouse.services.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.order.OrderDTO;
import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductDTO;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.entities.Order;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import danila.mediasoft.test.warehouse.exceptions.ForbiddenException;
import danila.mediasoft.test.warehouse.exceptions.InvalidStatusException;
import danila.mediasoft.test.warehouse.exceptions.ResourceNotFoundException;
import danila.mediasoft.test.warehouse.repositories.OrderRepository;
import danila.mediasoft.test.warehouse.services.customer.CustomerService;
import danila.mediasoft.test.warehouse.services.customer.provider.CustomerProvider;
import danila.mediasoft.test.warehouse.services.orderproduct.OrderProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final CustomerProvider customerProvider;
    private final OrderProductService orderProductService;
    private final ConversionService conversionService;

    @Override
    @Transactional
    public UUID create(CreateOrderRequest orderRequest) {
        Order order = Order.builder()
                .deliveryAddress(orderRequest.deliveryAddress())
                .customer(customerService.findById(customerProvider.getId()))
                .orderStatus(OrderStatus.CREATED)
                .build();
        order.setProducts(orderProductService.create(orderRequest.products(), order));
        return orderRepository.save(order).getId();
    }

    @Override
    @Transactional
    public OrderDTO update(Set<OrderProductRequest> products, UUID id) {
        Order order = getVerifiedOrder(id);
        order.addProduct(orderProductService.update(products, order));
        return conversionService.convert(order, OrderDTO.class);
    }

    @Override
    public OrderDTO get(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order by id: " + id + " not found"));
        if (!order.getCustomer().getId().equals(customerProvider.getId())) {
            throw new ForbiddenException("Customer id != Customer in order");
        }
        Set<OrderProductDTO> products = orderProductService.getByOrderId(id);
        return OrderDTO.builder()
                .orderId(id)
                .products(products)
                .price(products.stream()
                        .map(product ->
                                product.price()
                                        .multiply(BigDecimal.valueOf(product.quantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }

    @Override
    public void delete(UUID id) {
        Order order = getVerifiedOrder(id);
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public OrderDTO updateStatus(UpdateStatusRequest updateStatusRequest, UUID id) {
        Order order = getVerifiedOrder(id);
        order.setOrderStatus(updateStatusRequest.status());
        return conversionService.convert(orderRepository.save(order), OrderDTO.class);
    }

    private Order getVerifiedOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order by id: " + id + " not found"));
        if (!order.getCustomer().getId().equals(customerProvider.getId())) {
            throw new ForbiddenException("Customer id != Customer in order");
        }
        if (!order.getOrderStatus().equals(OrderStatus.CREATED)) {
            throw new InvalidStatusException("Order cannot be changed");
        }
        return order;
    }
}
