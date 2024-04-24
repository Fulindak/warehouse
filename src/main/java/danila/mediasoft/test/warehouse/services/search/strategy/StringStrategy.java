package danila.mediasoft.test.warehouse.services.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class StringStrategy implements  PredicateStrategy<String> {


    @Override
    public Predicate getEqPattern(Expression<String> expression, String value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(expression, value);
    }

    @Override
    public Predicate getBeginningPattern(Expression<String> expression, String value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(expression, value + "%");
    }

    @Override
    public Predicate getEndPattern(Expression<String> expression, String value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(expression, "%"+ value);
    }

    @Override
    public Predicate getLikePattern(Expression<String> expression, String value, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.lower(expression), "%" + value.toLowerCase() + "%");
    }
}
