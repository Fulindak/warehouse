package danila.mediasoft.test.warehouse.services.crm;

import danila.mediasoft.test.warehouse.config.CrmProperties;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrmServiceClient implements CrmService {
    private final CrmProperties properties;

    @Override
    public @Nullable Map<String, String> getInn(List<String> customerLogins) {
        WebClient webClient = WebClient.builder().baseUrl(properties.host()).build();
        return webClient
                .post()
                .uri(properties.methods().getInn())
                .body(Mono.just(customerLogins), List.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))
                .onErrorResume(ex -> {
                    log.error(ex.getMessage());
                    return Mono.empty();
                })
                .blockOptional()
                .orElse(null);
    }

    @Override
    public CompletableFuture<Map<String, String>> getAsyncInn(List<String> customerLogins) {
        return CompletableFuture.supplyAsync(() -> getInn(customerLogins));
    }
}
