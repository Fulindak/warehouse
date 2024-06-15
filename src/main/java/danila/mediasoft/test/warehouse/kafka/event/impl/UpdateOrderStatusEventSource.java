package danila.mediasoft.test.warehouse.kafka.event.impl;

import danila.mediasoft.test.warehouse.enums.Event;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import danila.mediasoft.test.warehouse.kafka.event.KafkaEventSource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateOrderStatusEventSource implements KafkaEventSource {
    private Event event;
    private UUID orderId;
    private OrderStatus status;
}
