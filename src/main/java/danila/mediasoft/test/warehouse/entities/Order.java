package danila.mediasoft.test.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<OrderProduct> products = new HashSet<>();

    public void addProduct(Set<OrderProduct> orderProducts) {
        products.addAll(orderProducts);
    }

    public void removeProduct(OrderProduct product) {
        products.remove(product);
    }
}
