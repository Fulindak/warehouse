package danila.mediasoft.test.warehouse.services.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;

public class LocalDateTimeStrategy implements PredicateStrategy<LocalDateTime> {
    @Override
    public Predicate getEqPattern(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate getBeginningPattern(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getEndPattern(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<LocalDateTime> expression, LocalDateTime value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(expression, value.minusDays(3), value.plusDays(3));
    }
}
