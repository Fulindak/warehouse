package danila.mediasoft.test.warehouse.delegate;

import danila.mediasoft.test.warehouse.services.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentDelegate implements JavaDelegate {
    private static final String SUCCESS_PAYMENT_STATUS = "SUCCESS";
    private final PaymentService paymentService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            String status = paymentService.payOrder(
                    UUID.fromString(delegateExecution.getVariable("orderId").toString()),
                    new BigDecimal(delegateExecution.getVariable("price").toString()),
                    delegateExecution.getVariable("accountNumber").toString()
            );
            if (status.equals(SUCCESS_PAYMENT_STATUS)) {
                delegateExecution.setVariable("paymentStatus", SUCCESS_PAYMENT_STATUS);
            }
        } catch (Exception e) {
            log.error("Delegate: {}; Exception: {}", this.getClass().getSimpleName(), e);
            throw new BpmnError("ERROR");
        }
    }
}
