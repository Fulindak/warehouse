package danila.mediasoft.test.warehouse.services.payment;

import danila.mediasoft.test.warehouse.config.PaymentProperties;
import danila.mediasoft.test.warehouse.dto.payment.PaymentRequest;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceClient implements PaymentService {
    private final PaymentProperties properties;

    @Override
    public @Nullable String payOrder(UUID orderId, BigDecimal price, String accountNumber) {
        WebClient webClient = WebClient.builder().baseUrl(properties.host()).build();
        PaymentRequest request = new PaymentRequest(orderId, price, accountNumber);
        return webClient.post()
                .uri(properties.methods().postPay())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))
                .onErrorResume(ex -> {
                    log.error(ex.getMessage());
                    return Mono.empty();
                })
                .blockOptional()
                .orElse(null);
    }
}
