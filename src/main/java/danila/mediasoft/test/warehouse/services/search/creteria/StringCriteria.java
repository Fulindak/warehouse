package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.StringStrategy;

public class StringCriteria implements Criteria<String> {
    private final PredicateStrategy<String> strategy = new StringStrategy();
    private String field;
    private String value;
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
