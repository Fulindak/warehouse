package danila.mediasoft.test.warehouse.services.delivery;

import danila.mediasoft.test.warehouse.config.DeliveryProperties;
import danila.mediasoft.test.warehouse.dto.delivery.DeliveryRequest;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceClient implements DeliveryService {
    private final DeliveryProperties properties;

    @Override
    public @Nullable LocalDate createDelivery(DeliveryRequest request) {
        WebClient webClient = WebClient.builder().baseUrl(properties.host()).build();
        return webClient
                .post()
                .uri(properties.methods().postCreate())
                .bodyValue(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<LocalDate>() {
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
    public void cancelDelivery(UUID orderId) {
        WebClient webClient = WebClient.builder().baseUrl(properties.host()).build();
        webClient
                .post()
                .uri(properties.methods().postCreate())
                .bodyValue(BodyInserters.fromValue(orderId))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)));
    }
}
