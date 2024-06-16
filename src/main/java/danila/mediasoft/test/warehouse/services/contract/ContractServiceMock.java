package danila.mediasoft.test.warehouse.services.contract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "rest.contract-service", name = "mock", havingValue = "true")
@Primary
public class ContractServiceMock implements ContractService {
    @Override
    public UUID createContract(String inn, String accountNumber) {
        return UUID.randomUUID();
    }

    @Override
    public void removeContract(UUID contractId) {
        log.info("Contract {} remove", contractId);
    }
}
