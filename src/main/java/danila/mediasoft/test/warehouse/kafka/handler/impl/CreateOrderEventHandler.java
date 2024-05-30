package danila.mediasoft.test.warehouse.kafka.handler.impl;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.enums.EventStatus;
import danila.mediasoft.test.warehouse.kafka.event.Event;
import danila.mediasoft.test.warehouse.kafka.event.impl.CreateOrderEvent;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class CreateOrderEventHandler implements EventHandler<CreateOrderEvent> {
    private final OrderService orderService;

    @Override
    public Boolean canHandle(Event event) {
        Assert.notNull(event, "Event must be not null");
        return EventStatus.CREATE_ORDER.equals(event.getEvent());
    }

    @Override
    public String handlerEvent(CreateOrderEvent event) {
        CreateOrderRequest order = CreateOrderRequest.builder()
                .deliveryAddress(event.getDeliveryAddress())
                .products(event.getProducts())
                .build();
        return orderService.create(order, event.getCustomerId()).toString();
    }
}
