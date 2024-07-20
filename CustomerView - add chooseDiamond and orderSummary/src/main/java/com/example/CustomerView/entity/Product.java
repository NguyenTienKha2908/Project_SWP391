package com.example.CustomerView.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Id")
    private int product_Id;

    @NotBlank(message = "Product code is mandatory")
    @Column(name = "Product_Code", nullable = false)
    private String product_Code;

    @Column(name = "Product_Name", nullable = true)
    private String product_Name;

    @Column(name = "Collection_Id", nullable = true)
    private Integer collection_Id;

    @Column(name = "Category_Id", nullable = false)
    private int category_Id;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "Gender", nullable = true)
    private String gender;

    @Column(name = "Size", nullable = true)
    private Integer size;

    @Column(name = "Img_Url", nullable = true)
    private String img_Url;

    @Column(name = "Status", nullable = true)
    private Boolean status;

    // Getters and setters
    public int getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(int product_Id) {
        this.product_Id = product_Id;
    }

    public String getProduct_Code() {
        return product_Code;
    }

    public void setProduct_Code(String product_Code) {
        this.product_Code = product_Code;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public Integer getCollection_Id() {
        return collection_Id;
    }

    public void setCollection_Id(Integer collection_Id) {
        this.collection_Id = collection_Id;
    }

    public int getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(int category_Id) {
        this.category_Id = category_Id;
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getImg_Url() {
        return img_Url;
    }

    public void setImg_Url(String img_Url) {
        this.img_Url = img_Url;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
