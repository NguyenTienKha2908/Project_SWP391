package swp391.showdetail.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Material_Id")
    private Integer materialId;

    @Column(name = "Material_Code")
    private String materialCode;

    @Column(name = "Material_Name")
    private String materialName;

    private boolean status;

    @Column(name = "Img_Url")
    private String imgUrl;
}
