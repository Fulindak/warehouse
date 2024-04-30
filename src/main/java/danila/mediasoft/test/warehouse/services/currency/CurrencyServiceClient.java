package danila.mediasoft.test.warehouse.services.currency;

import danila.mediasoft.test.warehouse.enums.CurrencyType;

import java.math.BigDecimal;

public interface CurrencyServiceClient {
    BigDecimal getExchangeRate(CurrencyType currencyType);
}
