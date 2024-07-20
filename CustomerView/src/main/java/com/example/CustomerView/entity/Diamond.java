package com.example.CustomerView.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Entity
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
    private int status; // 1 for active, 0 for inactive

    @Column(name = "product_id", nullable = true)
    private Integer product_Id;

    @NotBlank(message = "Image URL is mandatory")
    @Column(name = "img_url", nullable = false)
    private String img_Url;

    @Lob
    @Column(name = "image_data", nullable = true)
    private byte[] image_Data;

    @Transient
    private MultipartFile img_File;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public byte[] getImageData() {
        return image_Data;
    }

    public void setImageData(byte[] image_Data) {
        this.image_Data = image_Data;
    }

    public MultipartFile getImgFile() {
        return img_File;
    }

    public void setImgFile(MultipartFile img_File) {
        this.img_File = img_File;
    }
}
