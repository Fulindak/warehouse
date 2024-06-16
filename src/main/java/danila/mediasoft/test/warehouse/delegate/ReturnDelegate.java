package danila.mediasoft.test.warehouse.delegate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReturnDelegate implements JavaDelegate {
    private final RuntimeService runtimeService;
    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            runtimeService
                    .createProcessInstanceModification(delegateExecution.getProcessInstanceId())
                    .startAfterActivity("Activity_0fc80hz")
                    .execute();
        } catch (Exception e) {
            log.error("Delegate: {}; Exception: {}", this.getClass().getSimpleName(), e);
            throw new BpmnError("ERROR");
        }
    }
}
