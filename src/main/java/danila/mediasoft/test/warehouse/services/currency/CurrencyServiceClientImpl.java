package danila.mediasoft.test.warehouse.services.currency;

import danila.mediasoft.test.warehouse.config.CurrencyProperties;
import danila.mediasoft.test.warehouse.dto.currency.CurrencyRates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@CacheConfig(cacheNames = "currencyRates")
@ConditionalOnMissingBean(name = "currencyServiceMock")
@RequiredArgsConstructor
public class CurrencyServiceClientImpl implements CurrencyServiceClient {
    private final WebClient webClient;
    private final CurrencyProperties properties;

    @Override
    @Cacheable(cacheNames = "currencyRates", key = "#root.methodName", unless = "#result == null")
    public CurrencyRates getCurrencyRates() {
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
                .orElseThrow(null);
    }

    @CacheEvict(allEntries = true, cacheNames = "currencyRates")
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void cacheEvict() {
        // TODO Scheduling clear cache
    }
}
