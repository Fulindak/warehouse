package danila.mediasoft.test.warehouse.entities;

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
    private Order order;
    @ManyToOne
    @MapsId("productId")
    private Product product;
    @Column(name = "quantity", nullable = false)
    private Long quantity = 0L;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}