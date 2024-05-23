package danila.mediasoft.test.warehouse.services.crm;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(prefix = "rest.crm-service", name = "mock", havingValue = "true")
@Primary
public class CrmServiceMock implements CrmService {
    private static final String NUMERIC_STRING = "0123456789";
    private static final Integer INN_LENGTH = 12;

    @Override
    public Map<String, String> getInn(List<String> customerLogins) {
        return customerLogins.stream().collect(Collectors.toMap(
                it -> it,
                it -> generateInn()
        ));
    }

    @Override
    public CompletableFuture<Map<String, String>> getAsyncInn(List<String> customerLogins) {
        return CompletableFuture.supplyAsync(() -> getInn(customerLogins));
    }

    private String generateInn() {
        StringBuilder sb = new StringBuilder(INN_LENGTH);
        for (int i = 0; i < 20; i++) {
            int index = (int) (NUMERIC_STRING.length() * Math.random());
            sb.append(NUMERIC_STRING.charAt(index));
        }
        return sb.toString();
    }
}
