package danila.mediasoft.test.warehouse.services.currency.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import danila.mediasoft.test.warehouse.config.CurrencyProperties;
import danila.mediasoft.test.warehouse.dto.currency.CurrencyRates;
import danila.mediasoft.test.warehouse.enums.CurrencyType;
import danila.mediasoft.test.warehouse.exceptions.CurrencyFileWriteException;
import danila.mediasoft.test.warehouse.services.currency.CurrencyServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExchangeProvider {
    private final CurrencyServiceClient client;
    private final ObjectMapper objectMapper;
    private final CurrencyProperties properties;

    public CurrencyRates getCurrencyRates() {
        return Optional.ofNullable(client.getCurrencyRates())
                .orElseGet(this::getExchangeRateFromFile);
    }

    public BigDecimal getExchangeRate(CurrencyType currency) {
        return getCurrencyRates().getRateByCurrencyType(currency);
    }
    private CurrencyRates getExchangeRateFromFile() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(properties.filePath())));
            return CurrencyRates.builder()
                    .rates(objectMapper
                            .readValue(jsonContent, new TypeReference<Map<String, BigDecimal>>() {
                            }))
                    .build();
        } catch (Exception e) {
            throw new CurrencyFileWriteException("Failed to read the file correctly.");
        }
    }
}
