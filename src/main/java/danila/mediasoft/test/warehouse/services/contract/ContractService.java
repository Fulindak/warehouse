package danila.mediasoft.test.warehouse.services.contract;

import java.util.UUID;

public interface ContractService {
    UUID createContract(String inn, String accountNumber);
    void removeContract(UUID contractId);
}
