package danila.mediasoft.test.warehouse.kafka.event.impl;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.enums.Event;
import danila.mediasoft.test.warehouse.kafka.event.KafkaEventSource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CreateOrderEventSource implements KafkaEventSource {
    private Event event;
    private Long customerId;
    private String deliveryAddress;
    private Set<OrderProductRequest> products;
}
