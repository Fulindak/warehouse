package danila.mediasoft.test.warehouse.services.account;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(prefix = "rest.account-service", name = "mock", havingValue = "true")
@Primary
public class AccountServiceMock implements AccountService {
    private static final String NUMERIC_STRING = "0123456789";
    private static final Integer BANK_ACCOUNT_LENGTH = 20;

    @Override
    public Map<String, String> getAccounts(List<String> customerLogins) {
        return customerLogins.stream().collect(Collectors.toMap(
                it -> it,
                it -> generateBankAccount()
        ));
    }

    @Override
    public CompletableFuture<Map<String, String>> getAsyncAccounts(List<String> customerLogins) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return
                    getAccounts(customerLogins);
        });
    }

    private String generateBankAccount() {
        StringBuilder sb = new StringBuilder(BANK_ACCOUNT_LENGTH);
        for (int i = 0; i < 20; i++) {
            int index = (int) (NUMERIC_STRING.length() * Math.random());
            sb.append(NUMERIC_STRING.charAt(index));
        }
        return sb.toString();
    }
}
