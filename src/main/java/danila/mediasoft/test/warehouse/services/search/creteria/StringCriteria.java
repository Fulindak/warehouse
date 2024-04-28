package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.StringStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class StringCriteria implements Criteria<String> {

    private static final PredicateStrategy<String> strategy = new StringStrategy();
    @NotNull
    private String field;
    @NotNull
    private String value;
    @NotNull
    private Operation operation;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public Operation getOperation() {
        return operation;
    }

    @Override
    public PredicateStrategy<String> getStrategy() {
        return strategy;
    }
}
