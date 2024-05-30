package danila.mediasoft.test.warehouse.kafka.handler.impl;

import danila.mediasoft.test.warehouse.enums.EventStatus;
import danila.mediasoft.test.warehouse.kafka.event.Event;
import danila.mediasoft.test.warehouse.kafka.event.impl.UpdateOrderEvent;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderEventHandler implements EventHandler<UpdateOrderEvent> {
    private final OrderService orderService;

    @Override
    public Boolean canHandle(Event event) {
        return EventStatus.UPDATE_ORDER.equals(event.getEvent());
    }

    @Override
    public String handlerEvent(UpdateOrderEvent event) {
        orderService.update(event.getProducts(), event.getOrderId(), event.getCustomerId());
        return event.getOrderId().toString();
    }
}
