package danila.mediasoft.test.warehouse.services.search;

import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.services.search.creteria.Criteria;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProductSpecification {
    public Specification<Product> findProductByCriteria(List<Criteria> criteriaList) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

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
                    case GREATER_THAN_OR_EQ -> predicates.add(strategy.getBeginningPattern(root.get(criteria.getField()),
                            criteria.getValue(),
                            criteriaBuilder));
                }

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
