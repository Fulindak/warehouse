package danila.mediasoft.test.warehouse.kafka.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import danila.mediasoft.test.warehouse.kafka.event.Event;
import danila.mediasoft.test.warehouse.kafka.event.impl.CreateOrderEvent;
import danila.mediasoft.test.warehouse.kafka.event.impl.DeleteOrderEvent;
import danila.mediasoft.test.warehouse.kafka.event.impl.UpdateOrderEvent;
import danila.mediasoft.test.warehouse.kafka.event.impl.UpdateOrderStatusEvent;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "event")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderEvent.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderEvent.class, name = "UPDATE_ORDER"),
        @JsonSubTypes.Type(value = DeleteOrderEvent.class, name = "DELETE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderStatusEvent.class, name = "UPDATE_ORDER_STATUS")
})
public interface KafkaEvent extends Event {
}
