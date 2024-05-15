package danila.mediasoft.test.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder, CurrencyProperties properties) {
        return webClientBuilder.baseUrl(properties.host()).build();
    }
}
