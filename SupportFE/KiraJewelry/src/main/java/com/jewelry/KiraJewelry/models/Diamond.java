package com.jewelry.KiraJewelry.models;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Diamond")
public class Diamond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dia_id")
    private int dia_Id;

    @NotBlank(message = "Diamond code is mandatory")
    @Column(name = "dia_code", nullable = false)
    private String dia_Code;

    @NotBlank(message = "Diamond name is mandatory")
    @Column(name = "dia_name", nullable = false)
    private String dia_Name;

    @NotBlank(message = "Origin is mandatory")
    @Column(name = "origin", nullable = false)
    private String origin;

    @NotNull(message = "Carat weight is mandatory")
    @Column(name = "carat_weight", nullable = false)
    private float carat_Weight;

    @NotBlank(message = "Color is mandatory")
    @Column(name = "color", nullable = false)
    private String color;

    @NotBlank(message = "Clarity is mandatory")
    @Column(name = "clarity", nullable = false)
    private String clarity;

    @NotBlank(message = "Cut is mandatory")
    @Column(name = "cut", nullable = false)
    private String cut;

    @NotBlank(message = "Proportions are mandatory")
    @Column(name = "proportions", nullable = false)
    private String proportions;

    @NotBlank(message = "Polish is mandatory")
    @Column(name = "polish", nullable = false)
    private String polish;

    @NotBlank(message = "Symmetry is mandatory")
    @Column(name = "symmetry", nullable = false)
    private String symmetry;

    @NotBlank(message = "Fluorescence is mandatory")
    @Column(name = "fluorescence", nullable = false)
    private String fluorescence;

    @Column(name = "status", nullable = false)
    private boolean status; // 1 for active, 0 for inactive

    @Column(name = "product_id", nullable = true)
    private Integer product_Id;

    @Column(name = "img_url", nullable = false)
    private String img_Url;

    // @Transient
    // private MultipartFile img_File;

    @NotNull(message = "Q Price is mandatory")
    @Column(name = "q_price", nullable = false)
    private Float q_price;

    @NotNull(message = "O Price is mandatory")
    @Column(name = "o_price", nullable = false)
    private Float o_price;

    // Getters and setters
    public int getDiaId() {
        return dia_Id;
    }

    public void setDiaId(int dia_Id) {
        this.dia_Id = dia_Id;
    }

    public String getDiaCode() {
        return dia_Code;
    }

    public void setDiaCode(String dia_Code) {
        this.dia_Code = dia_Code;
    }

    public String getDiaName() {
        return dia_Name;
    }

    public void setDiaName(String dia_Name) {
        this.dia_Name = dia_Name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public float getCaratWeight() {
        return carat_Weight;
    }

    public void setCaratWeight(float carat_Weight) {
        this.carat_Weight = carat_Weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getProportions() {
        return proportions;
    }

    public void setProportions(String proportions) {
        this.proportions = proportions;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getSymmetry() {
        return symmetry;
    }

    public void setSymmetry(String symmetry) {
        this.symmetry = symmetry;
    }

    public String getFluorescence() {
        return fluorescence;
    }

    public void setFluorescence(String fluorescence) {
        this.fluorescence = fluorescence;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getProductId() {
        return product_Id;
    }

    public void setProductId(Integer product_Id) {
        this.product_Id = product_Id;
    }

    public String getImgUrl() {
        return img_Url;
    }

    public void setImgUrl(String img_Url) {
        this.img_Url = img_Url;
    }
    public Float getQ_price() {
        return q_price;
    }

    public void setQ_price(Float q_price) {
        this.q_price = q_price;
    }

    public Float getO_price() {
        return o_price;
    }

    public void setO_price(Float o_price) {
        this.o_price = o_price;
    }
}
