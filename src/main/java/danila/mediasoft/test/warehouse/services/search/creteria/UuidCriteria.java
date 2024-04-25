package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.UuidStrategy;
import lombok.Builder;

import java.util.UUID;

@Builder
public class UuidCriteria implements Criteria<UUID> {
    private final PredicateStrategy<UUID> strategy = new UuidStrategy();
    private String field;
    private UUID value;
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
