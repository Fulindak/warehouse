package danila.mediasoft.test.warehouse.services.notification;

import danila.mediasoft.test.warehouse.config.NotificationProperties;
import danila.mediasoft.test.warehouse.dto.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceClient implements NotificationService {
    private final NotificationProperties properties;
    @Override
    public void sendMsg(NotificationRequest request) {
        WebClient webClient = WebClient.builder().baseUrl(properties.host()).build();
        webClient
                .post()
                .uri(properties.methods().postNotify())
                .bodyValue(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)));
    }
}
