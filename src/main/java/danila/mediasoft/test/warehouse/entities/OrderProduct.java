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
    @Id
    @SequenceGenerator(name = "order_product_seq",
            sequenceName = "order_product_seq",
            allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_product_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;
    @Column(name = "quantity")
    private Long quantity = 0L;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
