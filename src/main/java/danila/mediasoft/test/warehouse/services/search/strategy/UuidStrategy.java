package danila.mediasoft.test.warehouse.services.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.util.UUID;

public class UuidStrategy implements PredicateStrategy<UUID> {
    @Override
    public Predicate getEqPattern(Expression<UUID> expression, UUID value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate getBeginningPattern(Expression<UUID> expression, UUID value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate getEndPattern(Expression<UUID> expression, UUID value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<UUID> expression, UUID value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }
}
