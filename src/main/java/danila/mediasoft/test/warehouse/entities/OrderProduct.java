package danila.mediasoft.test.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_product")
public class OrderProduct {
    @EmbeddedId
    private OrderProductId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "quantity")
    private Long quantity = 0L;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
