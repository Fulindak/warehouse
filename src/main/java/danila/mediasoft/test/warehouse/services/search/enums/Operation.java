package danila.mediasoft.test.warehouse.services.search.enums;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Operation {
    @JsonProperty("operation")
    @JsonAlias({"="})
    EQUAL,

    @JsonProperty("operation")
    @JsonAlias({">="})
    GRATER_THAN_OR_EQ,

    @JsonProperty("operation")
    @JsonAlias({"<="})
    LESS_THAN_OR_EQ,

    @JsonProperty("operation")
    @JsonAlias({"~"})
    LIKE;
}
