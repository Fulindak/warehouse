package danila.mediasoft.test.warehouse.kafka.handler.impl;

import danila.mediasoft.test.warehouse.enums.Event;
import danila.mediasoft.test.warehouse.kafka.event.EventSource;
import danila.mediasoft.test.warehouse.kafka.event.impl.DeleteOrderEventSource;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class DeleteOrderEventHandler implements EventHandler<DeleteOrderEventSource> {
    private final OrderService orderService;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return Event.DELETE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handlerEvent(DeleteOrderEventSource event) {
        orderService.delete(event.getOrderId(), event.getCustomerId());
        return event.getOrderId().toString();
    }
}
