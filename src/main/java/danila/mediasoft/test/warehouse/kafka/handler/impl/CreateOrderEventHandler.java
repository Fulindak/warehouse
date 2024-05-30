package danila.mediasoft.test.warehouse.kafka.handler.impl;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import danila.mediasoft.test.warehouse.enums.Event;
import danila.mediasoft.test.warehouse.kafka.event.EventSource;
import danila.mediasoft.test.warehouse.kafka.event.impl.CreateOrderEventSource;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class CreateOrderEventHandler implements EventHandler<CreateOrderEventSource> {
    private final OrderService orderService;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return Event.CREATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handlerEvent(CreateOrderEventSource event) {
        CreateOrderRequest order = CreateOrderRequest.builder()
                .deliveryAddress(event.getDeliveryAddress())
                .products(event.getProducts())
                .build();
        return orderService.create(order, event.getCustomerId()).toString();
    }
}
