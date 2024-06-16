package danila.mediasoft.test.warehouse.delegate.compensate;

import danila.mediasoft.test.warehouse.services.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelDeliveryDelegate implements JavaDelegate {
    private final DeliveryService deliveryService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            deliveryService.cancelDelivery(UUID.fromString(
                    delegateExecution.getVariable("orderId").toString())
            );
        } catch (Exception e) {
            log.error("Delegate: {}; Exception: {}", this.getClass().getSimpleName(), e);
            throw new BpmnError("COMPENSATE_ERROR");
        }
    }
}
