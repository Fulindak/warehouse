package danila.mediasoft.test.warehouse.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "product_type")
public class ProductType {
    @Id
    @SequenceGenerator(name = "product_type_seq",
            sequenceName = "product_type_seq",
            allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_type_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;


}
