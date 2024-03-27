package danila.mediasoft.test.warehouse.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProfileConf {

    @Value("${application.current-profile}")
    private String profile;

    @EventListener(ContextRefreshedEvent.class)
    public void onStartUp() {
        log.debug("The current profile is : " +  profile);

    }
}
