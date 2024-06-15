package danila.mediasoft.test.warehouse.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import danila.mediasoft.test.warehouse.kafka.event.EventSource;
import danila.mediasoft.test.warehouse.kafka.event.KafkaEventSource;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled")
public class OrderConsumer {
    private final Set<EventHandler<EventSource>> eventHandlers;

    @KafkaListener(topics = "test_topic1", groupId = "group1", containerFactory = "kafkaListenerContainerFactoryByte")
    public void orderEventListenTopic(byte[] message)  throws JsonProcessingException {
        log.info("Message: {}", message);
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final KafkaEventSource eventSource = objectMapper.readValue(message, KafkaEventSource.class);
            log.info("Event: {}", eventSource);
            eventHandlers.stream()
                    .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(eventSource))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Handler not found"))
                    .handlerEvent(eventSource);

        } catch (IOException e) {
            log.error("Couldn't parse message: {}; exception: ", message, e);
        }
    }
}
