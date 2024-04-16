package danila.mediasoft.test.warehouse.schedulers;

import danila.mediasoft.test.warehouse.annotations.LogExecutTime;
import danila.mediasoft.test.warehouse.entities.Product;
import danila.mediasoft.test.warehouse.repositories.ProductJDBCRepository;
import danila.mediasoft.test.warehouse.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Profile("!h2")
@Slf4j
@Component
@ConditionalOnProperty(value = "app.scheduling.enable", havingValue = "true")
public class OptimizedScheduler {

    @Value("${app.batch.size}")
    private int batchSize;
    private final ProductJDBCRepository productJDBCRepository;
    private final ProductRepository productRepository;

    public OptimizedScheduler(ProductJDBCRepository productJDBCRepository, ProductRepository productRepository) {
        this.productJDBCRepository = productJDBCRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @LogExecutTime
    @Scheduled(fixedRateString = "${app.scheduling.interval}")
    @ConditionalOnProperty(value = "app.scheduling.optimize", havingValue = "true")
    public void scheduleTaskUsingCronExpression() {
        int page = 0;
        Pageable pageable = PageRequest.of(page, batchSize);
        while (true) {
          List<Product> productList = productRepository.findAll(pageable).getContent();

            if (productList.isEmpty()) {
                break;
            }

            productJDBCRepository.batchUpdateProduct(productList, batchSize);
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
