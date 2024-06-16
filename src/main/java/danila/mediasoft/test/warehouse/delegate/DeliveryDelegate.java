package danila.mediasoft.test.warehouse.delegate;

import danila.mediasoft.test.warehouse.dto.delivery.DeliveryRequest;
import danila.mediasoft.test.warehouse.services.delivery.DeliveryService;
import danila.mediasoft.test.warehouse.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryDelegate implements JavaDelegate {
    private final DeliveryService deliveryService;
    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            DeliveryRequest request = new DeliveryRequest(delegateExecution.getVariable("deliveryAddress").toString(),
                    UUID.fromString(delegateExecution.getVariable("orderId").toString()));
            LocalDate deliveryDate = deliveryService.createDelivery(request);
            delegateExecution.setVariable("deliveryDate", deliveryDate);
        } catch (Exception e) {
            log.error("Delegate: {}; Exception: {}", this.getClass().getSimpleName(), e);
            throw new BpmnError("ERROR");
        }
    }
}
