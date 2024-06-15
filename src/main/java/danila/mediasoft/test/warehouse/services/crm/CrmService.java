package danila.mediasoft.test.warehouse.services.crm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface CrmService {
    Map<String, String> getInn(List<String> customerLogins);
    CompletableFuture<Map<String, String>> getAsyncInn(List<String> customerLogins);
}
