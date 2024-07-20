package com.jewelry.KiraJewelry.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "product_design")
public class ProductDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_design_id")
    private int product_Design_Id;

    @NotBlank(message = "Product design code is mandatory")
    @Column(name = "product_design_code", nullable = false)
    private String product_Design_Code;

    @Column(name = "product_id", nullable = false)
    private int product_Id;

    @Column(name = "product_design_name", nullable = true)
    private String product_Design_Name;

    @Column(name = "category_id", nullable = true)
    private Integer category_Id;

    @Column(name = "collection_id", nullable = true)
    private Integer collection_Id;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "product_size", nullable = true)
    private Integer product_Size;

    @Column(name = "product_design_shell_id", nullable = true)
    private Integer product_Design_Shell_Id;

    @Column(name = "gem_min_size", nullable = true)
    private Float gem_Min_Size;

    @Column(name = "gem_max_size", nullable = true)
    private Float gem_Max_Size;

    @Column(name = "status", nullable = true)
    private Boolean status;

    // Getters and setters
    public int getProduct_Design_Id() {
        return product_Design_Id;
    }

    public void setProduct_Design_Id(int product_Design_Id) {
        this.product_Design_Id = product_Design_Id;
    }

    public String getProduct_Design_Code() {
        return product_Design_Code;
    }

    public void setProduct_Design_Code(String product_Design_Code) {
        this.product_Design_Code = product_Design_Code;
    }

    public String getProduct_Design_Name() {
        return product_Design_Name;
    }

    public void setProduct_Design_Name(String product_Design_Name) {
        this.product_Design_Name = product_Design_Name;
    }

    public Integer getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(Integer category_Id) {
        this.category_Id = category_Id;
    }

    public Integer getCollection_Id() {
        return collection_Id;
    }

    public void setCollection_Id(Integer collection_Id) {
        this.collection_Id = collection_Id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getProduct_Size() {
        return product_Size;
    }

    public void setProduct_Size(Integer product_Size) {
        this.product_Size = product_Size;
    }

    public Integer getProduct_Design_Shell_Id() {
        return product_Design_Shell_Id;
    }

    public void setProduct_Design_Shell_Id(Integer product_Design_Shell_Id) {
        this.product_Design_Shell_Id = product_Design_Shell_Id;
    }

    public Float getGem_Min_Size() {
        return gem_Min_Size;
    }

    public void setGem_Min_Size(Float gem_Min_Size) {
        this.gem_Min_Size = gem_Min_Size;
    }

    public Float getGem_Max_Size() {
        return gem_Max_Size;
    }

    public void setGem_Max_Size(Float gem_Max_Size) {
        this.gem_Max_Size = gem_Max_Size;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(int product_Id) {
        this.product_Id = product_Id;
    }
}

