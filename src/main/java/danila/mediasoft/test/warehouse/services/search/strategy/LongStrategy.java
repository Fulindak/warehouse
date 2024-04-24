package danila.mediasoft.test.warehouse.services.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class LongStrategy implements PredicateStrategy<Long> {
    @Override
    public Predicate getEqPattern(Expression<Long> expression, Long value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate getBeginningPattern(Expression<Long> expression, Long value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getEndPattern(Expression<Long> expression, Long value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<Long> expression, Long value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(expression, (long)(value * 0.8)),
                criteriaBuilder.greaterThanOrEqualTo(expression, (long)(value * 1.1))
        );
    }
}
