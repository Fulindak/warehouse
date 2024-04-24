package danila.mediasoft.test.warehouse.services.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;


public interface PredicateStrategy<T> {
    Predicate getEqPattern(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);
    Predicate getBeginningPattern (Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);
    Predicate getEndPattern(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);
    Predicate getLikePattern(Expression<T> expression, T value, CriteriaBuilder criteriaBuilder);
}
