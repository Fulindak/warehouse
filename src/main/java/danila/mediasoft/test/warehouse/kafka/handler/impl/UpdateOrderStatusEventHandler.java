package danila.mediasoft.test.warehouse.kafka.handler.impl;

import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.enums.Event;
import danila.mediasoft.test.warehouse.kafka.event.EventSource;
import danila.mediasoft.test.warehouse.kafka.event.impl.UpdateOrderStatusEventSource;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusEventHandler implements EventHandler<UpdateOrderStatusEventSource> {
    private final OrderService orderService;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        return Event.UPDATE_ORDER_STATUS.equals(eventSource.getEvent());
    }

    @Override
    public String handlerEvent(UpdateOrderStatusEventSource event) {
        return orderService.updateStatus(
                        UpdateStatusRequest.builder()
                                .status(event.getStatus())
                                .build(),
                        event.getOrderId())
                .orderId()
                .toString();
    }
}
