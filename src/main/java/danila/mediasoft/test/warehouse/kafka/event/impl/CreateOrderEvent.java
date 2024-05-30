package danila.mediasoft.test.warehouse.kafka.event.impl;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.enums.EventStatus;
import danila.mediasoft.test.warehouse.kafka.event.KafkaEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public final class CreateOrderEvent implements KafkaEvent {
    private final EventStatus event;
    private final Long customerId;
    private final String deliveryAddress;
    private final Set<OrderProductRequest> products;
}
