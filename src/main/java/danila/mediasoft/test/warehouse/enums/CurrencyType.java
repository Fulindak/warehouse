package danila.mediasoft.test.warehouse.enums;

import jakarta.annotation.Nullable;

public enum CurrencyType {
    CNY, USD, EUR, RUB;

    public static @Nullable CurrencyType fromValue(String value) {
        for (CurrencyType type : CurrencyType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
