package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.LocalDateStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class LocalDateCriteria implements Criteria<LocalDate> {
    private static final PredicateStrategy<LocalDate> strategy = new LocalDateStrategy();
    @NotNull
    private String field;
    @NotNull
    private LocalDate value;
    @NotNull
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
