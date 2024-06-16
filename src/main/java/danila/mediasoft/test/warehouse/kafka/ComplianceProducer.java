package danila.mediasoft.test.warehouse.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import danila.mediasoft.test.warehouse.dto.camunda.ComplianceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class ComplianceProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplateByte;
    public void sendCustomerInfo(final String topic, final String key, ComplianceRequest request) throws JsonProcessingException {
        Assert.hasText(topic, "topic must not be blank");
        Assert.hasText(key, "key must not be blank");
        Assert.notNull(request, "KafkaEvent must not be null");
        final ObjectMapper objectMapper = new ObjectMapper();
        final byte[] data = objectMapper.writeValueAsBytes(request);
        kafkaTemplateByte.send(topic, key, data);
    }
}
