package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "Diamond")
public class Diamond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gem_id")
    private int gem_Id;

    @NotBlank(message = "Gem code is mandatory")
    @Column(name = "gem_code", nullable = false)
    private String gem_Code;

    @NotBlank(message = "Gem name is mandatory")
    @Column(name = "gem_name", nullable = false)
    private String gem_Name;

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
    private byte[] imageData;

    @Transient
    private MultipartFile imgFile;

    // Getters and setters
    public int getGemId() {
        return gem_Id;
    }

    public void setGemId(int gem_Id) {
        this.gem_Id = gem_Id;
    }

    public String getGemCode() {
        return gem_Code;
    }

    public void setGemCode(String gem_Code) {
        this.gem_Code = gem_Code;
    }

    public String getGemName() {
        return gem_Name;
    }

    public void setGemName(String gem_Name) {
        this.gem_Name = gem_Name;
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
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public MultipartFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }
}

