package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.LocalDateTimeStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;

import java.time.LocalDateTime;

public class LocalDateTimeCriteria implements Criteria<LocalDateTime> {

    private final PredicateStrategy<LocalDateTime> strategy = new LocalDateTimeStrategy();
    private String field;
    private LocalDateTime value;
    private Operation operation;

    @Override
    public LocalDateTime getValue() {
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
    public PredicateStrategy<LocalDateTime> getStrategy() {
        return strategy;
    }
}
