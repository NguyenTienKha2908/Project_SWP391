package swp391.paymentcod_banking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;
    @Column(name = "product_name")
    private String productname;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;

    // getters and setters
}