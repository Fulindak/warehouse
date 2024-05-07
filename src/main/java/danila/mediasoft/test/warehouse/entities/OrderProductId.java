package danila.mediasoft.test.warehouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderProductId implements Serializable {
    @Column(name = "order_id")
    private UUID orderId;
    @Column(name = "product_id")
    private UUID productId;
}
