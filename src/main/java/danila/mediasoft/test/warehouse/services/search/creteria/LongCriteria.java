package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.LongStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;

public class LongCriteria implements Criteria<Long> {

    private final PredicateStrategy<Long> strategy = new LongStrategy();
    private String field;
    private Long value;
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
