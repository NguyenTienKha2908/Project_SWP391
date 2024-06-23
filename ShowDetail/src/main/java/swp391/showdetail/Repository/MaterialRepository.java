package swp391.showdetail.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swp391.showdetail.DTO.MaterialDto;
import swp391.showdetail.models.Material;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Integer> {


//    @Query("SELECT new swp391.showdetail.DTO.MaterialDto(mpl.materialId, m.Material_Name, mpl.Price, mpl.Eff_Date) " +
//            "FROM Material m JOIN swp391.showdetail.models.MaterialPriceList mpl ON m.Material_Id = mpl.materialId")

      @Query("SELECT new swp391.showdetail.DTO.MaterialDto(mpl.materialId, m.materialName, mpl.price, mpl.effDate) " +
            "FROM Material m JOIN MaterialPriceList mpl ON m.materialId = mpl.materialId")
    List<MaterialDto> findMaterialDetails();

}
