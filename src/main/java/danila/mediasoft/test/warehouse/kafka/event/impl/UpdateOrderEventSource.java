package danila.mediasoft.test.warehouse.kafka.event.impl;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.enums.Event;
import danila.mediasoft.test.warehouse.kafka.event.EventSource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateOrderEventSource implements EventSource {
    private Event event;
    private Long customerId;
    private UUID orderId;
    private Set<OrderProductRequest> products;
}

