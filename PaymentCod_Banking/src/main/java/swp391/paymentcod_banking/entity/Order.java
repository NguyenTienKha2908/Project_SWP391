package swp391.paymentcod_banking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "payment-status")
    private String paymentStatus;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "shipping_status")
    private String shippingStatus;

}