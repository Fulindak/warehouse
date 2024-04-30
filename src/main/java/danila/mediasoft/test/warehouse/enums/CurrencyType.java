package danila.mediasoft.test.warehouse.enums;

import danila.mediasoft.test.warehouse.exceptions.IllegalCurrencyTypeException;

public enum CurrencyType {
    CNY, USD, EUR, RUB;

    public static CurrencyType formValue(String value) {
        for (CurrencyType type : CurrencyType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalCurrencyTypeException("Unsupported currency type: " + value);
    }
}
