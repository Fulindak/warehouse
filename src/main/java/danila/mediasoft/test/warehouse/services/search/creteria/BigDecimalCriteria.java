package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.BigDecimalPredicateStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class BigDecimalCriteria implements Criteria<BigDecimal> {
    private static final PredicateStrategy<BigDecimal> strategy = new BigDecimalPredicateStrategy();
    @NotNull
    private String field;
    @NotNull
    private BigDecimal value;
    @NotNull
    private Operation operation;

    @Override
    public BigDecimal getValue() {
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
    public PredicateStrategy<BigDecimal> getStrategy() {
        return strategy;
    }
}
