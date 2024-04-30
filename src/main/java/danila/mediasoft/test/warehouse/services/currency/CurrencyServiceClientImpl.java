package danila.mediasoft.test.warehouse.services.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import danila.mediasoft.test.warehouse.dto.currency.CurrencyRates;
import danila.mediasoft.test.warehouse.enums.CurrencyType;
import danila.mediasoft.test.warehouse.exceptions.CurrencyFileWriteException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

@Service
@CacheConfig(cacheNames = "currencyRates")
public class CurrencyServiceClientImpl implements CurrencyServiceClient {
    private final WebClient webClient;
    private final String methodGetCurrency;
    private final String filePath;
    private final ObjectMapper objectMapper;

    public CurrencyServiceClientImpl(WebClient.Builder webClient,
                                     @Value("${currency-service.host}") String baseURL,
                                     @Value("${currency-service.methods.get-currency}") String methodGetCurrency,
                                     @Value("${currency-service.filePath}") String filePath,
                                     ObjectMapper objectMapper) {
        this.methodGetCurrency = methodGetCurrency;
        this.filePath = filePath;
        this.objectMapper = objectMapper;
        this.webClient = webClient.baseUrl(baseURL).build();
    }

    @Override
    public BigDecimal getExchangeRate(CurrencyType currencyType) {
        CurrencyRates rates = getCurrencyRatesFromServer()
                .onErrorResume(
                        error -> getCurrencyRatesFromFile()
                ).block();
        return rates.getRateByCurrencyType(currencyType);
    }

    @Cacheable(cacheNames = "currencyRates", key = "#root.methodName", unless = "#result == null")
    private Mono<CurrencyRates> getCurrencyRatesFromServer() {
        return webClient.get()
                .uri(methodGetCurrency)
                .retrieve()
                .bodyToMono(CurrencyRates.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)));
    }

    @Cacheable(cacheNames = "currencyRates", key = "#root.methodName", unless = "#result == null")

    private Mono<CurrencyRates> getCurrencyRatesFromFile() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            return Mono.just(objectMapper.readValue(jsonContent, CurrencyRates.class));
        } catch (Exception e) {
            throw new CurrencyFileWriteException("Failed to read the file correctly.");
        }
    }

    @CacheEvict(allEntries = true, cacheNames = "currencyRates")
    @Scheduled(fixedDelay = 30000)
    public void cacheEvict() {
        // TODO document why this method is empty
    }
}
