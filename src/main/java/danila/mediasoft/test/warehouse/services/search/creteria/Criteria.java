package danila.mediasoft.test.warehouse.services.search.creteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import danila.mediasoft.test.warehouse.services.search.enums.Operation;
import danila.mediasoft.test.warehouse.services.search.strategy.PredicateStrategy;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "field", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "name", value = StringCriteria.class),
        @JsonSubTypes.Type(name = "updateAt", value = LocalDateTimeCriteria.class),
        @JsonSubTypes.Type(name = "quantity", value = LongCriteria.class),
        @JsonSubTypes.Type(name = "price", value = BigDecimalCriteria.class),
        @JsonSubTypes.Type(name = "createAt", value = LocalDateCriteria.class),
        @JsonSubTypes.Type(name = "id", value = UuidCriteria.class)
})
public interface Criteria<T> {
    T getValue();

    String getField();

    Operation getOperation();

    PredicateStrategy<T> getStrategy();
}
