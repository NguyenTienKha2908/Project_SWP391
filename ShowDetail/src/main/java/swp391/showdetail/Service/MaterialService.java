package swp391.showdetail.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp391.showdetail.DTO.MaterialDto;
import swp391.showdetail.Repository.MaterialPriceListRepository;
import swp391.showdetail.Repository.MaterialRepository;
import swp391.showdetail.models.Material;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public List<MaterialDto> findMaterialDetails() {
        return materialRepository.findMaterialDetails();
    }

}

