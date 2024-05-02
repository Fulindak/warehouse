package danila.mediasoft.test.warehouse.dto.currency;

import danila.mediasoft.test.warehouse.enums.CurrencyType;
import danila.mediasoft.test.warehouse.exceptions.IllegalCurrencyTypeException;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class CurrencyRates {
    private Map<String, BigDecimal> rates = new HashMap<>();

    public BigDecimal getRateByCurrencyType(CurrencyType currencyType) {
        if (!rates.containsKey(currencyType.name())) {
            throw new IllegalCurrencyTypeException("Illegal currency type: " + currencyType.name());
        }
        return rates.get(currencyType.name());
    }
}
