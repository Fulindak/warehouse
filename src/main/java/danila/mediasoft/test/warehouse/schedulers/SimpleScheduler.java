package danila.mediasoft.test.warehouse.schedulers;

import danila.mediasoft.test.warehouse.annotations.LogExecutTime;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("!h2")
@Component
@Slf4j
@ConditionalOnProperty("app.scheduling.enable")
@ConditionalOnMissingBean(name = "optimizedScheduler")
public class SimpleScheduler {

    private final ProductRepository productRepository;

    @Value("${app.scheduling.rate}")
    private double rate;

    public SimpleScheduler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @LogExecutTime
    @Scheduled(fixedRateString = "${app.scheduling.interval}")
    public void increasePrice() {
        List<Product> productList = productRepository.findAll();
        productRepository.saveAll(productList.stream().map(o -> o.setPrice((long)(o.getPrice() * rate))).toList());
    }
}
