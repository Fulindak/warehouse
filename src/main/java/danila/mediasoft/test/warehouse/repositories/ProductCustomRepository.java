package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Value("${app.scheduling.rate}")
    private double rate;

    public void batchUpdateProduct(List<Product> products) {
        entityManager.unwrap(org.hibernate.Session.class).doWork(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE product SET price = ? WHERE id = ?")) {
                for (Product product : products) {
                    ps.setLong(1, (long)(product.getPrice() * rate));
                    ps.setObject(2, product.getId());
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to execute batch update", e);
            }
        });
    }
}
