package danila.mediasoft.test.warehouse.kafka.event.impl;

import danila.mediasoft.test.warehouse.dto.orderproduct.OrderProductRequest;
import danila.mediasoft.test.warehouse.enums.EventStatus;
import danila.mediasoft.test.warehouse.kafka.event.Event;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public final class UpdateOrderEvent implements Event {
    private final EventStatus event;
    private final Long customerId;
    private final UUID orderId;
    private final Set<OrderProductRequest> products;
}

