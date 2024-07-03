package swp391.paymentcod_banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp391.paymentcod_banking.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}