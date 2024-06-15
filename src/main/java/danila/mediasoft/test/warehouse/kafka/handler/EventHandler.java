package danila.mediasoft.test.warehouse.kafka.handler;

import danila.mediasoft.test.warehouse.kafka.event.EventSource;

public interface EventHandler<T extends EventSource> {
    Boolean canHandle(EventSource eventSource);

    String handlerEvent(T event);
}
