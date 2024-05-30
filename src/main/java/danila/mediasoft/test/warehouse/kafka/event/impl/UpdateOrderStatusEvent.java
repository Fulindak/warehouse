package danila.mediasoft.test.warehouse.kafka.event.impl;

import danila.mediasoft.test.warehouse.enums.EventStatus;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import danila.mediasoft.test.warehouse.kafka.event.KafkaEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public final class UpdateOrderStatusEvent implements KafkaEvent {
    private final EventStatus event;
    private final UUID orderId;
    private final OrderStatus status;
}
