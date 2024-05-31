package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Material")  
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaterialId")  
    private int materialId;

    @Column(name = "MaterialCode", nullable = false)  
    private String materialCode;

    @Column(name = "MaterialName", nullable = false)  
    private String materialName;

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    
}

