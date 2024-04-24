package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.LocalDateStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;

import java.time.LocalDate;

public class LocalDateCriteria implements Criteria<LocalDate> {
    private final PredicateStrategy<LocalDate> strategy = new LocalDateStrategy();
    private String field;
    private LocalDate value;
    private Operation operation;

    @Override
    public LocalDate getValue() {
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
    public PredicateStrategy<LocalDate> getStrategy() {
        return strategy;
    }
}
