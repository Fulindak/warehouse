package danila.mediasoft.test.warehouse.enums;

public enum CurrencyType {
    CNY, USD, EUR, RUB;

    public static CurrencyType fromValue(String value) {
        for (CurrencyType type : CurrencyType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
