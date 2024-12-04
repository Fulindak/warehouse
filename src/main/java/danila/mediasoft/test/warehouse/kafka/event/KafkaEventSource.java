package danila.mediasoft.test.warehouse.kafka.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import danila.mediasoft.test.warehouse.kafka.event.impl.CreateOrderEventSource;
import danila.mediasoft.test.warehouse.kafka.event.impl.DeleteOrderEventSource;
import danila.mediasoft.test.warehouse.kafka.event.impl.UpdateOrderEventSource;
import danila.mediasoft.test.warehouse.kafka.event.impl.UpdateOrderStatusEventSource;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "event")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderEventSource.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderEventSource.class, name = "UPDATE_ORDER"),
        @JsonSubTypes.Type(value = DeleteOrderEventSource.class, name = "DELETE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderStatusEventSource.class, name = "UPDATE_ORDER_STATUS")
})
public interface KafkaEventSource extends EventSource {
}
