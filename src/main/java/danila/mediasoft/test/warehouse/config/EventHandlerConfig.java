package danila.mediasoft.test.warehouse.config;

import danila.mediasoft.test.warehouse.kafka.event.EventSource;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class EventHandlerConfig {
    @Bean
    <T extends EventSource> Set<EventHandler<T>> eventHandlers(Set<EventHandler<T>> eventHandlers) {
        return new HashSet<>(eventHandlers);
    }
}