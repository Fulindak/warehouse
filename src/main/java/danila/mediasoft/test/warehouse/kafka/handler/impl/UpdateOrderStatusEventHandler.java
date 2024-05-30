package danila.mediasoft.test.warehouse.kafka.handler.impl;

import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.enums.EventStatus;
import danila.mediasoft.test.warehouse.kafka.event.Event;
import danila.mediasoft.test.warehouse.kafka.event.impl.UpdateOrderStatusEvent;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusEventHandler implements EventHandler<UpdateOrderStatusEvent> {
    private final OrderService orderService;

    @Override
    public Boolean canHandle(Event event) {
        return EventStatus.UPDATE_ORDER_STATUS.equals(event.getEvent());
    }

    @Override
    public String handlerEvent(UpdateOrderStatusEvent event) {
        return orderService.updateStatus(
                        UpdateStatusRequest.builder()
                                .status(event.getStatus())
                                .build(),
                        event.getOrderId())
                .orderId()
                .toString();
    }
}
