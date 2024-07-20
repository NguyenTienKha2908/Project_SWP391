package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Category_Id")
    private int category_Id;

    @NotBlank(message = "Category name is mandatory")
    @Column(name = "Category_Name", nullable = false)
    private String category_Name;

    @NotNull(message = "Status is mandatory")
    @Column(name = "Status", nullable = false)
    private boolean status;

    @Column(name = "Img_Url", nullable = true)
    private String img_Url;

    @Lob
    @Column(name = "Image_Data", nullable = true)
    private byte[] imageData;

    // Getters and setters
    public int getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(int category_Id) {
        this.category_Id = category_Id;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImg_Url() {
        return img_Url;
    }

    public void setImg_Url(String img_Url) {
        this.img_Url = img_Url;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
