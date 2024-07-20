package com.example.demo.entity;

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

    public void setMaterialCode(String material_Code) {
        this.material_Code = material_Code;
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
}
