package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Material_Id")
    private int materialId;

    @Column(name = "Material_Code", nullable = false)
    private String materialCode;

    @Column(name = "Material_Name", nullable = false)
    private String materialName;

    @Column(name = "Status", nullable = false)
    private int status; // 1 for active, 0 for inactive

    // Getters and setters

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
