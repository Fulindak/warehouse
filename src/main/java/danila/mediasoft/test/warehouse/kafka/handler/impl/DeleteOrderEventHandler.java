package danila.mediasoft.test.warehouse.kafka.handler.impl;

import danila.mediasoft.test.warehouse.enums.EventStatus;
import danila.mediasoft.test.warehouse.kafka.event.Event;
import danila.mediasoft.test.warehouse.kafka.event.impl.DeleteOrderEvent;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class DeleteOrderEventHandler implements EventHandler<DeleteOrderEvent> {
    private final OrderService orderService;

    @Override
    public Boolean canHandle(Event event) {
        Assert.notNull(event, "Event must be not null");
        return EventStatus.DELETE_ORDER.equals(event.getEvent());
    }

    @Override
    public String handlerEvent(DeleteOrderEvent event) {
        orderService.delete(event.getOrderId(), event.getCustomerId());
        return event.getOrderId().toString();
    }
}
