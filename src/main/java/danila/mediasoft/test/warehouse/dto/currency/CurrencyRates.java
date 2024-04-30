package danila.mediasoft.test.warehouse.dto.currency;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import danila.mediasoft.test.warehouse.enums.CurrencyType;
import danila.mediasoft.test.warehouse.exceptions.IllegalCurrencyTypeException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@Data
public class CurrencyRates {
    private Map<String, BigDecimal> rates = new HashMap<>();

    @JsonAnySetter
    public void setRate(String currency, BigDecimal rate) {
        rates.put(currency, rate);
    }

    public BigDecimal getRateByCurrencyType(CurrencyType currencyType) {
        if (!rates.containsKey(currencyType.name())) {
            throw new IllegalCurrencyTypeException("Illegal currency type: " + currencyType.name());
        }
        return rates.get(currencyType.name());
    }
}
