package danila.mediasoft.test.warehouse.kafka.handler;

import danila.mediasoft.test.warehouse.kafka.event.Event;

public interface EventHandler<T extends Event> {
    Boolean canHandle(Event event);

    String handlerEvent(T event);
}
