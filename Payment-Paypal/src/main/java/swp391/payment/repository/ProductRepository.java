package swp391.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp391.payment.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
