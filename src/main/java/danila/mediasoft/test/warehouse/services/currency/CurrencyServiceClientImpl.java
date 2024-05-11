package danila.mediasoft.test.warehouse.services.currency;

import danila.mediasoft.test.warehouse.config.CurrencyProperties;
import danila.mediasoft.test.warehouse.dto.currency.CurrencyRates;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyServiceClientImpl implements CurrencyServiceClient {
    private final WebClient webClient;
    private final CurrencyProperties properties;

    @Override
    @Cacheable(cacheNames = "currencyRates", key = "#root.methodName", unless = "#result == null")
    public @Nullable CurrencyRates getCurrencyRates() {
        return webClient
                .get()
                .uri(properties.methods().getCurrency())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, BigDecimal>>() {
                })
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))
                .onErrorResume(ex -> {
                    log.error(ex.getMessage());
                    return Mono.empty();
                })
                .blockOptional()
                .map(ratesMap -> CurrencyRates.builder().rates(ratesMap).build())
                .orElse(null);
    }
}
