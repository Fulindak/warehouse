package danila.mediasoft.test.warehouse.kafka.event;

import danila.mediasoft.test.warehouse.enums.Event;

public interface EventSource {
    Event getEvent();
}
