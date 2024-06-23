package swp391.showdetail.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table(name = "Diamond")
@Getter
@Setter
@Entity
public class Diamond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Dia_Id", nullable = false)
    private Integer diaId;

    @Column(name = "Dia_Code", length = 255)
    private String diaCode;

    @Column(name = "Dia_Name", length = 255)
    private String diaName;

    @Column(name = "Origin", length = 255)
    private String origin;

    @Column(name = "Carat_Weight")
    private Float caratWeight;

    @Column(name = "Color", length = 10)
    private String color;

    @Column(name = "Clarity", length = 10)
    private String clarity;

    @Column(name = "Cut", length = 255)
    private String cut;

    @Column(name = "Proportions", length = 255)
    private String proportions;

    @Column(name = "Polish", length = 255)
    private String polish;

    @Column(name = "Symmetry", length = 255)
    private String symmetry;

    @Column(name = "Fluorescence", length = 10)
    private String fluorescence;

    @Column(name = "Status")
    private Boolean status;

    @Column(name = "Img_Url", length = 255, nullable = false)
    private String imgUrl;

    @Column(name = "Q_Price")
    private Float qPrice;

    @Column(name = "O_Price")
    private Float oPrice;

    @Column(name = "Product_Id")
    private Integer productId;

}

