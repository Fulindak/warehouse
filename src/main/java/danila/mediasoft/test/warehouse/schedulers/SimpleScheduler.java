package danila.mediasoft.test.warehouse.schedulers;

import danila.mediasoft.test.warehouse.annotations.LogExecutTime;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!h2")
@Component
@Slf4j
@ConditionalOnProperty(value = "app.scheduling.enable", havingValue = "true")
public class SimpleScheduler {

    private final ProductRepository productRepository;

    public SimpleScheduler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @LogExecutTime
    @Scheduled(fixedRateString = "${app.scheduling.interval}")
    @ConditionalOnProperty(value = "app.scheduling.optimize", havingValue = "false")
    public void scheduleTaskUsingCronExpression() {
        List<Product> productList = productRepository.findAll();
        productRepository.saveAll(productList.stream().map(o -> o.setPrice(o.getPrice() + 11)).toList());
    }
}
