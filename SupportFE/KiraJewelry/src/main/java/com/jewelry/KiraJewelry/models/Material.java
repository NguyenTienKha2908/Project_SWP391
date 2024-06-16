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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    public int getMaterialId() {
        return material_Id;
    }

    public void setMaterialId(int materialId) {
        this.material_Id = materialId;
    }

    public String getMaterialCode() {
        return material_Code;
    }

    public void setMaterialCode(String materialCode) {
        this.material_Code = materialCode;
    }

    public String getMaterialName() {
        return material_Name;
    }

    public void setMaterialName(String materialName) {
        this.material_Name = materialName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
