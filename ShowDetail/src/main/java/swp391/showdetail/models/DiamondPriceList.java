package swp391.showdetail.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Diamond_Price_List")
public class DiamondPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Origin", length = 255)
    private String origin;

    @Column(name = "Carat_Weight_From")
    private Float caratWeightFrom;

    @Column(name = "Carat_Weight_To")
    private Float caratWeightTo;

    @Column(name = "Color", length = 10)
    private String color;

    @Column(name = "Clarity", length = 10)
    private String clarity;

    @Column(name = "Cut", length = 255)
    private String cut;

    @Column(name = "Price")
    private Float price;

    @Column(name = "Eff_Date")
    private Date effDate;

}
