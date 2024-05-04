package danila.mediasoft.test.warehouse.services.search;

import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.services.search.creteria.Criteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@RequiredArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class ProductSpecification implements Specification<Product> {
    private final List<Criteria> criteriaList;

    @Override
    public Predicate toPredicate(
            @NotNull Root<Product> root,
            @NotNull CriteriaQuery<?> query,
            @NotNull CriteriaBuilder cb
    ) {
        final List<Predicate> predicates = criteriaList.stream().map(
                it -> {
                    switch (it.getOperation()) {
                        case EQUAL -> {
                            return it.getStrategy().getEqPattern(
                                    root.get(it.getField()),
                                    it.getValue(),
                                    cb
                            );
                        }
                        case GRATER_THAN_OR_EQ -> {
                            return it.getStrategy().getBeginningPattern(
                                    root.get(it.getField()),
                                    it.getValue(),
                                    cb
                            );
                        }
                        case LESS_THAN_OR_EQ -> {
                            return it.getStrategy().getEndPattern(
                                    root.get(it.getField()),
                                    it.getValue(),
                                    cb
                            );
                        }
                        case LIKE -> {
                            return it.getStrategy().getLikePattern(
                                    root.get(it.getField()),
                                    it.getValue(),
                                    cb
                            );
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + it.getOperation());
                    }
                }
        ).toList();
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
