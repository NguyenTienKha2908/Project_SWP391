package swp391.showdetail.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp391.showdetail.models.MaterialPriceList;

public interface MaterialPriceListRepository extends JpaRepository<MaterialPriceList, Integer> {
}
