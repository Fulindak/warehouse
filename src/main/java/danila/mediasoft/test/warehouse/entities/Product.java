package danila.mediasoft.test.warehouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id", updatable = false, nullable = false)
    private  UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "article", unique = true, nullable = false)
    private String article;

    @Column(name = "quantity")
    private Integer quantity = 0;

    @Column(name = "price", nullable = false)
    private Long price;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_product_type",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_type_id")
    )
    private Set<ProductType> productTypes = new HashSet<>();

    @UpdateTimestamp
    @Column(name = "update_at")
    private Date updateAt;

    @CreationTimestamp
    @Column(name = "create_at")
    private Date createAt;

    public Product setPrice(Long price) {
        this.price = price;
        return this;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setArticle(String article) {
        this.article = article;
        return this;
    }

    public Product setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
        return this;
    }

    public void addType(ProductType type) {
        productTypes.add(type);
    }

    public void removeType(ProductType type) {
        productTypes.remove(type);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", article='" + article + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}' + "\n";
    }
}
