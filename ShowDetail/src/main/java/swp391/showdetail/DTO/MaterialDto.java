package swp391.showdetail.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MaterialDto {
    private Integer materialId;
    private String materialName;
    private Float price;
    private Date effDate;

    // Constructor
    public MaterialDto(Integer materialId, String materialName, Float price, Date effDate) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.price = price;
        this.effDate = effDate;
    }

    // Getters and Setter
}
