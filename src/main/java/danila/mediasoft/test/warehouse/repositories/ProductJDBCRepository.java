package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductJDBCRepository {
    private final JdbcTemplate jdbcTemplate;



    public ProductJDBCRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void batchUpdateProduct(List<Product> products , Integer batchSize) {
        String sql = "UPDATE product SET price = ? WHERE id = ?";

        jdbcTemplate.batchUpdate(sql, products, batchSize, (ps, product) -> {
            ps.setLong(1, product.getPrice() + 10);
            ps.setObject(2, product.getId());
        });
    }
}
