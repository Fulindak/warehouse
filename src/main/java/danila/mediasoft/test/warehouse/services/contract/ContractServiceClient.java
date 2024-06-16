package danila.mediasoft.test.warehouse.services.contract;

import danila.mediasoft.test.warehouse.config.ContractProperties;
import danila.mediasoft.test.warehouse.dto.contract.ContractRequest;
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
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractServiceClient implements ContractService {
    private final ContractProperties properties;

    @Override
    public @Nullable UUID createContract(String inn, String accountNumber) {
        WebClient webClient = WebClient.builder().baseUrl(properties.host()).build();
        ContractRequest request = new ContractRequest(inn, accountNumber);
        return webClient
                .post()
                .uri(properties.methods().postCreate())
                .bodyValue(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<UUID>() {
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
    public void removeContract(UUID contractId) {
        WebClient webClient = WebClient.builder().baseUrl(properties.host()).build();
        webClient
                .post()
                .uri(properties.methods().postCreate())
                .bodyValue(BodyInserters.fromValue(contractId))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)));

    }
}
