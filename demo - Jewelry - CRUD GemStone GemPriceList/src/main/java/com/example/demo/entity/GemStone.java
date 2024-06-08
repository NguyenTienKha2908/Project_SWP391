package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "GemStone")
public class GemStone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Gem_Id")
    private int gemId;

    @Column(name = "Gem_Code", nullable = false)
    private String gemCode;

    @Column(name = "Gem_Name", nullable = false)
    private String gemName;

    @Column(name = "Origin", nullable = false)
    private String origin;

    @Column(name = "Carat_Weight", nullable = false)
    private float caratWeight;

    @Column(name = "Color", nullable = false)
    private String color;

    @Column(name = "Clarity", nullable = false)
    private String clarity;

    @Column(name = "Cut", nullable = false)
    private String cut;

    @Column(name = "Proportions", nullable = false)
    private String proportions;

    @Column(name = "Polish", nullable = false)
    private String polish;

    @Column(name = "Symmetry", nullable = false)
    private String symmetry;

    @Column(name = "Fluorescence", nullable = false)
    private String fluorescence;

    @Column(name = "Status", nullable = false)
    private int status; // 1 for active, 0 for inactive

    @Column(name = "Product_Id", nullable = true)
    private Integer productId;

    // Getters and setters

    public int getGemId() {
        return gemId;
    }

    public void setGemId(int gemId) {
        this.gemId = gemId;
    }

    public String getGemCode() {
        return gemCode;
    }

    public void setGemCode(String gemCode) {
        this.gemCode = gemCode;
    }

    public String getGemName() {
        return gemName;
    }

    public void setGemName(String gemName) {
        this.gemName = gemName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public float getCaratWeight() {
        return caratWeight;
    }

    public void setCaratWeight(float caratWeight) {
        this.caratWeight = caratWeight;
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
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
