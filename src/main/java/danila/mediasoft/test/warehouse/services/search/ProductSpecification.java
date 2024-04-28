package danila.mediasoft.test.warehouse.services.search;

import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.services.search.creteria.Criteria;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {
    private final List<Criteria> criteriaList;
    private final List<Predicate> predicates;

    public ProductSpecification(List<Criteria> criteria) {
        this.predicates = new ArrayList<>();
        this.criteriaList = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        for (Criteria criteria : criteriaList) {
            PredicateStrategy strategy = criteria.getStrategy();
            switch (criteria.getOperation()) {
                case LIKE -> predicates.add(strategy.getLikePattern(root.get(criteria.getField()),
                        criteria.getValue(),
                        criteriaBuilder));
                case EQUAL -> predicates.add(strategy.getEqPattern(root.get(criteria.getField()),
                        criteria.getValue(),
                        criteriaBuilder));
                case LESS_THAN_OR_EQ -> predicates.add(strategy.getEndPattern(root.get(criteria.getField()),
                        criteria.getValue(),
                        criteriaBuilder));
                case GRATER_THAN_OR_EQ -> predicates.add(strategy.getBeginningPattern(root.get(criteria.getField()),
                        criteria.getValue(),
                        criteriaBuilder));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
