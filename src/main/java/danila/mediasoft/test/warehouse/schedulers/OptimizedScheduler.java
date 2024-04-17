package danila.mediasoft.test.warehouse.schedulers;

import danila.mediasoft.test.warehouse.annotations.LogExecutTime;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.repositories.ProductCustomRepository;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Profile("!h2")
@Slf4j
@Component
@ConditionalOnProperty(
        name = { "app.scheduling.optimize", "app.scheduling.enable" },
        havingValue = "true"
)
public class OptimizedScheduler {

    @Value("${app.batch.size}")
    private int batchSize;

    @Value("${app.scheduling.pessimistic-lock}")
    private boolean lock;
    private final ProductCustomRepository productCustomRepository;
    private final ProductRepository productRepository;

    public OptimizedScheduler(ProductCustomRepository productCustomRepository, ProductRepository productRepository) {
        this.productCustomRepository = productCustomRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @LogExecutTime
    @Scheduled(fixedRateString = "${app.scheduling.interval}")
    public void increasePrice() {
        int page = 0;
        Pageable pageable = PageRequest.of(page, batchSize);
        while (true) {
          List<Product> productList = productRepository.findAll(pageable).getContent();

            if (productList.isEmpty()) {
                break;
            }
            if(lock) {
                productCustomRepository.batchUpdatePricePessimisticLock(productList);
            } else {
                productCustomRepository.batchUpdatePriceNoLock(productList);
            }
            page++;
            log.info("Total updated records: " + (page * batchSize));
            pageable = PageRequest.of(page, batchSize);
            writeProductsInFile(productList);
        }
    }

    private static void writeProductsInFile(List<Product> products) {
        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter("src/products.txt"))) {
            for (Product product : products) {
                writer.write(product.toString());
        }
        } catch (IOException e) {
            log.error("An error occurred while writing to file: " + e.getMessage());
        }
    }
}
