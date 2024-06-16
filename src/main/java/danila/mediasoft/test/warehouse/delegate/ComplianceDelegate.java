package danila.mediasoft.test.warehouse.delegate;

import danila.mediasoft.test.warehouse.dto.camunda.ComplianceRequest;
import danila.mediasoft.test.warehouse.kafka.ComplianceProducer;
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
public class ComplianceDelegate implements JavaDelegate {
    private final ComplianceProducer complianceProducer;
    private static final String COMPLIANCE_TOPIC_NAME = "compliance";

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            ComplianceRequest request = new ComplianceRequest(delegateExecution.getVariable("login").toString(),
                    delegateExecution.getVariable("inn").toString(),
                    UUID.fromString(delegateExecution.getBusinessKey()));
            complianceProducer.sendCustomerInfo(COMPLIANCE_TOPIC_NAME, delegateExecution.getProcessInstanceId(), request);
        } catch (Exception e) {
            log.error("Delegate: {}; Exception: {}", this.getClass().getSimpleName(), e);
            throw new BpmnError("ERROR");
        }
    }
}
