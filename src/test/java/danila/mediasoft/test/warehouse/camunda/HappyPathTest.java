package danila.mediasoft.test.warehouse.camunda;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import danila.mediasoft.test.warehouse.WarehouseApplication;
import danila.mediasoft.test.warehouse.dto.customer.CreateCustomerRequest;
import danila.mediasoft.test.warehouse.dto.delivery.DeliveryRequest;
import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.dto.product.CreateProductDTO;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import danila.mediasoft.test.warehouse.repositories.OrderRepository;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import danila.mediasoft.test.warehouse.services.ProductService;
import danila.mediasoft.test.warehouse.services.contract.ContractService;
import danila.mediasoft.test.warehouse.services.customer.CustomerService;
import danila.mediasoft.test.warehouse.services.delivery.DeliveryService;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import danila.mediasoft.test.warehouse.services.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {WarehouseApplication.class})
@ActiveProfiles(profiles = {"h2"})
@WireMockTest
public class HappyPathTest {
    @Autowired
    ContractService contractService;
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderRepository orderRepository;
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("rest.payment-service.host", wireMockExtension::baseUrl);
        registry.add("rest.delivery-service.host", wireMockExtension::baseUrl);
        registry.add("rest.contract-service.host", wireMockExtension::baseUrl);
    }

    @BeforeEach
    void setUp() {
        wireMockExtension.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/v1/pay"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("SUCCESS")));
        wireMockExtension.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/v1/contract/create"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("123e4567-e89b-12d3-a456-426614174000")));
        wireMockExtension.stubFor(WireMock.post(WireMock.urlPathEqualTo("/api/v1/delivery/create"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("2024-06-18")));
    }

    @Test
    void testContractService() {
        UUID contractId = contractService.createContract("122", "122");
        assertThat(contractId).isEqualTo(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    void testDeliveryService() {
        DeliveryRequest request = new DeliveryRequest("address", UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        LocalDate localDate = deliveryService.createDelivery(request);
        assertThat(localDate).isEqualTo(LocalDate.parse("2024-06-18"));
    }

    @Test
    void testPaymentService() {
        String status = paymentService.payOrder(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                BigDecimal.valueOf(50),
                "number");
        assertThat(status).isEqualTo("SUCCESS");
    }

    @Test
    void testAllPath() {
        productRepository.deleteAll();
        UUID productId = productService.createProduct(CreateProductDTO.builder()
                .name("name")
                .article("article")
                .price(BigDecimal.valueOf(50))
                .types(List.of())
                .quantity(50L)
                .build());
        Long customerId = customerService.create(new CreateCustomerRequest("danila", "danila"));
        OrderProductRequest request = new OrderProductRequest(productId, 4L);
        Set<OrderProductRequest> set = new HashSet<>();
        set.add(request);
        UUID orderId = orderService.create(new CreateOrderRequest("address", set), customerId);
        orderService.confirm(orderId, customerId);
        await()
                .atMost(10, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    OrderStatus status = orderRepository.findById(orderId).get().getOrderStatus();
                    return OrderStatus.CONFIRMED.equals(status);
                });
        OrderStatus status = orderRepository.findById(orderId).get().getOrderStatus();
        assertThat(status).isEqualTo(OrderStatus.CONFIRMED);
    }
}
