package danila.mediasoft.test.warehouse.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import danila.mediasoft.test.warehouse.dto.camunda.ComplianceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled")
public class ComplianceConsumer {
    private final RuntimeService runtimeService;

    @KafkaListener(topics = "compliance_response", groupId = "group1", containerFactory = "kafkaListenerContainerFactoryByte")
    public void orderEventListenTopic(byte[] message) throws JsonProcessingException {
        log.info("Message: {}", message);
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final ComplianceResponse response = objectMapper.readValue(message, ComplianceResponse.class);
            log.info("Response: {}", response);
            runtimeService.createMessageCorrelation("continueProcessMsg")
                    .processInstanceBusinessKey(response.businessKey().toString())
                    .setVariable("complianceStatus", response.status())
                    .correlate();
        } catch (IOException e) {
            log.error("Couldn't parse message: {}; exception: ", message, e);
        }
    }
}
