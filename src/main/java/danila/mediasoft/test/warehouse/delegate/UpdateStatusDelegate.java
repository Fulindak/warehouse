package danila.mediasoft.test.warehouse.delegate;

import danila.mediasoft.test.warehouse.dto.order.UpdateStatusRequest;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import danila.mediasoft.test.warehouse.repositories.OrderRepository;
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
public class UpdateStatusDelegate implements JavaDelegate {
    private static final String ERROR_PAYMENT_STATUS = "FAILED";
    private static final String SUCCESS_PAYMENT_STATUS = "SUCCESS";
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            UpdateStatusRequest statusRequest;
            String paymentStatus = delegateExecution.getVariable("paymentStatus").toString();
            switch (paymentStatus) {
                case ERROR_PAYMENT_STATUS -> {
                    statusRequest = new UpdateStatusRequest(OrderStatus.REJECTED);
                    orderService.updateStatus(statusRequest,
                            UUID.fromString(delegateExecution.getVariable("orderId").toString()));
                }
                case SUCCESS_PAYMENT_STATUS -> orderService.updateStatusAndSetDeliveryDate(
                        UUID.fromString(delegateExecution.getVariable("orderId").toString()),
                        OrderStatus.CONFIRMED,
                        LocalDate.parse(delegateExecution.getVariable("deliveryDate").toString())
                );
                default -> throw new BpmnError("ERROR_STATUS");
            }
        } catch (Exception e) {
            log.error("Delegate: {}; Exception: {}", this.getClass().getSimpleName(), e);
            throw new BpmnError("ERROR");
        }
    }
}
