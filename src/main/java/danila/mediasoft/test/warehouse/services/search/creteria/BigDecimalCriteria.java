package danila.mediasoft.test.warehouse.services.search.creteria;

import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.BigDecimalPredicateStrategy;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class BigDecimalCriteria implements Criteria<BigDecimal> {

    private final PredicateStrategy<BigDecimal> strategy = new BigDecimalPredicateStrategy();
    private String field;
    private BigDecimal value;
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
