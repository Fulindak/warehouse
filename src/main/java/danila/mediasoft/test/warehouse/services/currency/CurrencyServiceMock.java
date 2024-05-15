package danila.mediasoft.test.warehouse.services.currency;

import danila.mediasoft.test.warehouse.dto.currency.CurrencyRates;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@ConditionalOnProperty(prefix = "rest.currency-service", name = "mock", havingValue = "true")
@Primary
public class CurrencyServiceMock implements CurrencyServiceClient {
    private final CurrencyRates currencyRates;

    public CurrencyServiceMock() {
        this.currencyRates = CurrencyRates.builder().rates(generateRandomRates()).build();
    }

    private BigDecimal generateRandomBigDecimal(BigDecimal max) {
        Random random = new Random();
        BigDecimal range = max.subtract(BigDecimal.ZERO);
        BigDecimal randomBigDecimal = BigDecimal.ZERO.add(range.multiply(BigDecimal.valueOf(random.nextDouble())));
        return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    private Map<String, BigDecimal> generateRandomRates() {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("CNY", generateRandomBigDecimal(new BigDecimal("20")));
        rates.put("USD", generateRandomBigDecimal(new BigDecimal("200")));
        rates.put("EUR", generateRandomBigDecimal(new BigDecimal("150")));
        return rates;
    }

    @Override
    public CurrencyRates getCurrencyRates() {
        return currencyRates;
    }
}
