package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    @EntityGraph(attributePaths = {"productTypes", "images"})
    @Override
    Optional<Product> findById(UUID uuid);

    @Modifying
    @Query("update Product u set u.price = :newPrice where u.id = :productId")
    void updatePrice(@Param("productId") UUID productId, @Param("newPrice") Long newPrice);

    @Modifying
    @Query("update Product u set u.quantity = :newQuantity where u.id = :productId")
    void updateQuantity(@Param("productId") UUID productId, @Param("newQuantity") Long newQuantity);

    Optional<Product> findByArticle(String article);
}