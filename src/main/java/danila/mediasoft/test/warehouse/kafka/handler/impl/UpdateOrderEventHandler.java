package danila.mediasoft.test.warehouse.kafka.handler.impl;

import danila.mediasoft.test.warehouse.enums.Event;
import danila.mediasoft.test.warehouse.kafka.event.EventSource;
import danila.mediasoft.test.warehouse.kafka.event.impl.UpdateOrderEventSource;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderEventHandler implements EventHandler<UpdateOrderEventSource> {
    private final OrderService orderService;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        return Event.UPDATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handlerEvent(UpdateOrderEventSource event) {
        orderService.update(event.getProducts(), event.getOrderId(), event.getCustomerId());
        return event.getOrderId().toString();
    }
}
