package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.UuidStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public class UuidCriteria implements Criteria<UUID> {
    private static final PredicateStrategy<UUID> strategy = new UuidStrategy();
    @NotNull
    private String field;
    @NotNull
    private UUID value;
    @NotNull
    private Operation operation;

    @Override
    public UUID getValue() {
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
    public PredicateStrategy<UUID> getStrategy() {
        return strategy;
    }
}
