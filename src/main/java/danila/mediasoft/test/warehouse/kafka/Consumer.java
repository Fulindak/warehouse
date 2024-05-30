package danila.mediasoft.test.warehouse.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import danila.mediasoft.test.warehouse.kafka.event.Event;
import danila.mediasoft.test.warehouse.kafka.event.KafkaEvent;
import danila.mediasoft.test.warehouse.kafka.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled")
public class Consumer {
    private final Set<EventHandler<Event>> eventHandlers;

    @KafkaListener(topics = "test_topic", groupId = "group1", containerFactory = "kafkaListenerContainerFactoryByte")
    public void listenTopic(byte[] message) {
        String messageString = new String(message, StandardCharsets.UTF_8);
        log.info("Message: {}", messageString);
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final KafkaEvent eventSource = objectMapper.readValue(message, KafkaEvent.class);
            log.info("Event: {}", eventSource);
            eventHandlers.stream()
                    .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(eventSource))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Handler for eventsource not found"))
                    .handlerEvent(eventSource);

        } catch (IOException e) {
            log.error("Couldn't parse message: {}; exception: ", message, e);
        }
    }
}
