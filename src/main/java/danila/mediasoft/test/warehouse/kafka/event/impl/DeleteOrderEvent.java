package danila.mediasoft.test.warehouse.kafka.event.impl;

import danila.mediasoft.test.warehouse.enums.EventStatus;
import danila.mediasoft.test.warehouse.kafka.event.KafkaEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public final class DeleteOrderEvent implements KafkaEvent {
    private final EventStatus event;
    private final Long customerId;
    private final UUID orderId;
}
