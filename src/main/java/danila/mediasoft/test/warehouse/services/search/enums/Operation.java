package danila.mediasoft.test.warehouse.services.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import danila.mediasoft.test.warehouse.exceptions.OperationNotFoundException;

import java.util.HashMap;
import java.util.Map;

public enum Operation {
    EQUAL("="),
    GREATER_THAN_OR_EQ(">="),
    LESS_THAN_OR_EQ("<="),
    LIKE("~");

    private final String symbol;

    private static final Map<String, Operation> symbolToEnum = new HashMap<>();
    private static final Map<String, Operation> wordToEnum = new HashMap<>();

    static {
        for (Operation op : values()) {
            symbolToEnum.put(op.symbol, op);
            wordToEnum.put(op.name(), op);
        }
    }

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @JsonCreator
    public static Operation fromString(String value) {
        Operation operation = symbolToEnum.getOrDefault(value, wordToEnum.get(value));
        if (operation == null) {
            throw new OperationNotFoundException("Unsupported operation: " + value).setOperationValue(value);
        }
        return symbolToEnum.getOrDefault(value, wordToEnum.get(value));
    }

    @JsonValue
    public String getValue() {
        return symbol;
    }
}
