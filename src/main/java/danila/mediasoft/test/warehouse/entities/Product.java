package danila.mediasoft.test.warehouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "article", unique = true, nullable = false)
    private String article;
    @Column(name = "quantity")
    private Long quantity = 0L;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_product_type",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_type_id")
    )
    private List<ProductType> productTypes;
    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "is_available")
    private Boolean isAvailable;

    public void addType(ProductType type) {
        productTypes.add(type);
    }

    public void removeType(ProductType type) {
        productTypes.remove(type);
    }
}
