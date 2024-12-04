package danila.mediasoft.test.warehouse.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer")
public class Customer {
    @Id
    @SequenceGenerator(name = "customer_seq",
            sequenceName = "customer_seq",
            allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "login", unique = true, nullable = false)
    private String login;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "is_active")
    private Boolean isActive;
    @OneToMany(mappedBy = "customer")
    @Fetch(FetchMode.JOIN)
    private Set<Order> orders;

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}
