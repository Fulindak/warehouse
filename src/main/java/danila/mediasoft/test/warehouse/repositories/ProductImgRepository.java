package danila.mediasoft.test.warehouse.repositories;

import danila.mediasoft.test.warehouse.entities.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, UUID> {
    @Modifying
    @Query(value = """
            insert into product_image (product_id, image_id, expansion)
            values (:productId, :imgId, :expansion)
            """, nativeQuery = true)
    void uploadImg(@Param("productId") UUID productId, @Param("imgId") UUID imgId, @Param("expansion") String expansion);
}
