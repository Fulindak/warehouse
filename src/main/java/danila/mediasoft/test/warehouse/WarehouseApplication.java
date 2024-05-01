package danila.mediasoft.test.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@ConfigurationPropertiesScan
public class WarehouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}
