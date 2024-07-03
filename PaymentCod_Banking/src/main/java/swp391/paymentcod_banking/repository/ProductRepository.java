package swp391.paymentcod_banking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import swp391.paymentcod_banking.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}