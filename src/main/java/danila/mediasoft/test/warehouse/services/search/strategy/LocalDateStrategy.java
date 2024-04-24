package danila.mediasoft.test.warehouse.services.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;

public class LocalDateStrategy implements PredicateStrategy<LocalDate> {

    @Override
    public Predicate getEqPattern(Expression<LocalDate> expression, LocalDate value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate getBeginningPattern(Expression<LocalDate> expression, LocalDate value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getEndPattern(Expression<LocalDate> expression, LocalDate value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<LocalDate> expression, LocalDate value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(expression, value.minusDays(3), value.plusDays(3));
    }
}
