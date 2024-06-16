package danila.mediasoft.test.warehouse.services.camunda;

import danila.mediasoft.test.warehouse.dto.camunda.ComplianceResponse;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("camunda")
@RequiredArgsConstructor
public class ConfirmEvent {
    private static final String PROCESS_KEY = "ConfirmEventProcessKey";
    private final RuntimeService runtimeService;

    public UUID startProcess(String deliveryAddress,
                             String inn,
                             String accountNumber,
                             BigDecimal price,
                             String login,
                             UUID orderId,
                             Long customerId) {
        final UUID businessKey = UUID.randomUUID();
        runtimeService
                .createProcessInstanceByKey(PROCESS_KEY)
                .businessKey(businessKey.toString())
                .setVariable("deliveryAddress", deliveryAddress)
                .setVariable("inn", inn)
                .setVariable("accountNumber", accountNumber)
                .setVariable("price", price)
                .setVariable("login", login)
                .setVariable("errorFlag", false)
                .setVariable("orderId", orderId)
                .setVariable("customerId", customerId)
                .setVariable("paymentStatus", "FAILED")
                .execute();
        return businessKey;
    }

    @PostMapping("/process/continue")
    public void continueProcess(@RequestBody ComplianceResponse response) {
        runtimeService
                .createMessageCorrelation("continueProcessMsg")
                .processInstanceBusinessKey(response.businessKey().toString())
                .setVariable("complianceStatus", response.status())
                .correlate();
    }
}
