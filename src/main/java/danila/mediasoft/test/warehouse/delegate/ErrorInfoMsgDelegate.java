package danila.mediasoft.test.warehouse.delegate;

import danila.mediasoft.test.warehouse.dto.notification.NotificationRequest;
import danila.mediasoft.test.warehouse.services.notification.NotificationService;
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
public class ErrorInfoMsgDelegate implements JavaDelegate {
    private static final String ERROR_MSG = "ЗАКАЗ ОТМЕННЕН";
    private final NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            NotificationRequest request = new NotificationRequest(ERROR_MSG,
                    UUID.fromString(delegateExecution.getVariable("orderId").toString()),
                    Long.getLong(delegateExecution.getVariable("customerId").toString()));
            notificationService.sendMsg(request);
        } catch (Exception e) {
            log.error("Delegate: {}; Exception: {}", this.getClass().getSimpleName(), e);
            throw new BpmnError("ERROR");
        }
    }
}
