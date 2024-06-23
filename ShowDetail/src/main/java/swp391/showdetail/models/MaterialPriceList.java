package swp391.showdetail.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Material_Price_List")
public class MaterialPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float price;

    @Column(name = "Eff_Date")
    private Date effDate;

    @Column(name = "Material_Id")
    private Integer materialId; // Đổi từ String sang Integer để đồng bộ với Material entity

}
