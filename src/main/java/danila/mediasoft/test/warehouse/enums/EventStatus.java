package danila.mediasoft.test.warehouse.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventStatus {
    @JsonProperty("event")
    CREATE_ORDER,
    @JsonProperty("event")
    UPDATE_ORDER,
    @JsonProperty("event")
    DELETE_ORDER,
    @JsonProperty("event")
    UPDATE_ORDER_STATUS
}
