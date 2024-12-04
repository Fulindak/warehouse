package danila.mediasoft.test.warehouse.kafka.event.impl;

import danila.mediasoft.test.warehouse.enums.Event;
import danila.mediasoft.test.warehouse.kafka.event.KafkaEventSource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class DeleteOrderEventSource implements KafkaEventSource {
    private Event event;
    private Long customerId;
    private UUID orderId;
}
