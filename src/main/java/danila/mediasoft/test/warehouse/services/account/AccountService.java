package danila.mediasoft.test.warehouse.services.account;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AccountService {
    Map<String, String> getAccounts(List<String> customerLogins);
    CompletableFuture<Map<String, String>> getAsyncAccounts(List<String> customerLogins);
}
