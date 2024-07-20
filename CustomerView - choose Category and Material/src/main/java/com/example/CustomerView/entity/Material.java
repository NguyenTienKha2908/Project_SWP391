package com.example.CustomerView.entity;

import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id", nullable = false)
    private int material_Id;

    @NotBlank(message = "Material code is mandatory")
    @Column(name = "material_code", nullable = false)
    private String material_Code;

    @NotBlank(message = "Material name is mandatory")
    @Size(max = 100, message = "Material name must be less than 100 characters")
    @Column(name = "material_name", nullable = false)
    private String material_Name;

    @Column(name = "status", nullable = false)
    private int status; // 1 for active, 0 for inactive

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "img_url")
    private String img_Url; 

    @Transient
    private MultipartFile imgFile;

    // Getters and setters
    public int getMaterial_Id() {
        return material_Id;
    }

    public void setMaterial_Id(int material_Id) {
        this.material_Id = material_Id;
    }

    public String getMaterial_Code() {
        return material_Code;
    }

    public void setMaterial_Code(String material_Code) {
        this.material_Code = material_Code;
    }

    public String getMaterial_Name() {
        return material_Name;
    }

    public void setMaterial_Name(String material_Name) {
        this.material_Name = material_Name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public MultipartFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }
}
    