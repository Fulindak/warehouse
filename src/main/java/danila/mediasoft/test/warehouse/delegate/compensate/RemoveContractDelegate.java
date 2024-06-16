package danila.mediasoft.test.warehouse.delegate.compensate;

import danila.mediasoft.test.warehouse.services.contract.ContractService;
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
public class RemoveContractDelegate implements JavaDelegate {
    private final ContractService contractService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            contractService.removeContract(
                    UUID.fromString(delegateExecution.getVariable("contractId").toString())
            );
        } catch (Exception e) {
            log.error("Delegate: {}; Exception: {}", this.getClass().getSimpleName(), e);
            throw new BpmnError("COMPENSATE_ERROR");
        }
    }
}
