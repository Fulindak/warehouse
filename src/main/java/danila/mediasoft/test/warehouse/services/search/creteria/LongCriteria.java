package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.LongStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class LongCriteria implements Criteria<Long> {
    private static final PredicateStrategy<Long> strategy = new LongStrategy();
    @NotNull
    private String field;
    @NotNull
    private Long value;
    @NotNull
    private Operation operation;

    @Override
    public Long getValue() {
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
    public PredicateStrategy<Long> getStrategy() {
        return strategy;
    }
}
