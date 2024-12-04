package danila.mediasoft.test.warehouse.services.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;

public class BigDecimalPredicateStrategy implements PredicateStrategy<BigDecimal> {
    @Override
    public Predicate getEqPattern(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate getBeginningPattern(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getEndPattern(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(expression, value.multiply(BigDecimal.valueOf(0.9))),
                criteriaBuilder.greaterThanOrEqualTo(expression, value.multiply(BigDecimal.valueOf(1.1)))
        );
    }
}
