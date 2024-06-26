package com.example.CustomerView.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Diamond")
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Dia_Id", nullable = false)
    private int dia_Id;

    @Column(name = "Dia_Code", nullable = false)
    private String dia_Code;

    @Column(name = "Dia_Name", nullable = false)
    private String dia_Name;

    @Column(name = "Origin", nullable = false)
    private String origin;

    @Column(name = "Carat_Weight", nullable = false)
    private float carat_Weight;

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
    private boolean status;

    @Column(name = "Img_Url", nullable = false)
    private String img_Url;

    @Column(name = "Q_Price", nullable = false)
    private float q_Price;

    @Column(name = "O_Price", nullable = false)
    private float o_Price;

    @Column(name = "Product_Id", nullable = false)
    private Integer product_Id;

    // Getters and setters
    public int getDia_Id() {
        return dia_Id;
    }

    public void setDia_Id(int dia_Id) {
        this.dia_Id = dia_Id;
    }

    public String getDia_Code() {
        return dia_Code;
    }

    public void setDia_Code(String dia_Code) {
        this.dia_Code = dia_Code;
    }

    public String getDia_Name() {
        return dia_Name;
    }

    public void setDia_Name(String dia_Name) {
        this.dia_Name = dia_Name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public float getCarat_Weight() {
        return carat_Weight;
    }

    public void setCarat_Weight(float carat_Weight) {
        this.carat_Weight = carat_Weight;
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

    public float getQ_Price() {
        return q_Price;
    }

    public void setQ_Price(float q_Price) {
        this.q_Price = q_Price;
    }

    public float getO_Price() {
        return o_Price;
    }

    public void setO_Price(float o_Price) {
        this.o_Price = o_Price;
    }

    public Integer getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(Integer product_Id) {
        this.product_Id = product_Id;
    }
}
