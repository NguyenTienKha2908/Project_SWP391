package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Collection_Id")
    private int collection_Id;

    @NotBlank(message = "Collection name is mandatory")
    @Column(name = "Collection_Name", nullable = false)
    private String collection_Name;

    @NotNull(message = "Status is mandatory")
    @Column(name = "Status", nullable = false)
    private boolean status;

    @Column(name = "Img_Url", nullable = true)
    private String img_Url;

    @Lob
    @Column(name = "Image_Data", nullable = true)
    private byte[] imageData;

    // Getters and setters
    public int getCollection_Id() {
        return collection_Id;
    }

    public void setCollection_Id(int collection_Id) {
        this.collection_Id = collection_Id;
    }

    public String getCollection_Name() {
        return collection_Name;
    }

    public void setCollection_Name(String collection_Name) {
        this.collection_Name = collection_Name;
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
