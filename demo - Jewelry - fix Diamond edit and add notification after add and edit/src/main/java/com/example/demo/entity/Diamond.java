package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "Diamond")
public class Diamond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dia_id")
    private int dia_id;

    @NotBlank(message = "Diamond code is mandatory")
    @Column(name = "dia_code", nullable = false)
    private String dia_code;

    @NotBlank(message = "Diamond name is mandatory")
    @Column(name = "dia_name", nullable = false)
    private String dia_name;

    @NotBlank(message = "Origin is mandatory")
    @Column(name = "origin", nullable = false)
    private String origin;

    @NotNull(message = "Carat weight is mandatory")
    @Column(name = "carat_weight", nullable = false)
    private Float carat_weight;

    @NotBlank(message = "Color is mandatory")
    @Column(name = "color", nullable = false)
    private String color;

    @NotBlank(message = "Clarity is mandatory")
    @Column(name = "clarity", nullable = false)
    private String clarity;

    @NotBlank(message = "Cut is mandatory")
    @Column(name = "cut", nullable = false)
    private String cut;

    @NotBlank(message = "Proportions are mandatory")
    @Column(name = "proportions", nullable = false)
    private String proportions;

    @NotBlank(message = "Polish is mandatory")
    @Column(name = "polish", nullable = false)
    private String polish;

    @NotBlank(message = "Symmetry is mandatory")
    @Column(name = "symmetry", nullable = false)
    private String symmetry;

    @NotBlank(message = "Fluorescence is mandatory")
    @Column(name = "fluorescence", nullable = false)
    private String fluorescence;

    @Column(name = "status", nullable = false)
    private int status; // 1 for active, 0 for inactive

    @Column(name = "product_id", nullable = true)
    private Integer product_id;

    @Column(name = "img_url", nullable = true)
    private String img_url;

    @Lob
    @Column(name = "image_data", nullable = true)
    private byte[] image_data;

    @Transient
    private MultipartFile img_file;

    @NotNull(message = "Q Price is mandatory")
    @Column(name = "q_price", nullable = false)
    private Float q_price;

    @NotNull(message = "O Price is mandatory")
    @Column(name = "o_price", nullable = false)
    private Float o_price;

    // Getters and setters
    public int getDia_id() {
        return dia_id;
    }

    public void setDia_id(int dia_id) {
        this.dia_id = dia_id;
    }

    public String getDia_code() {
        return dia_code;
    }

    public void setDia_code(String dia_code) {
        this.dia_code = dia_code;
    }

    public String getDia_name() {
        return dia_name;
    }

    public void setDia_name(String dia_name) {
        this.dia_name = dia_name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Float getCarat_weight() {
        return carat_weight;
    }

    public void setCarat_weight(Float carat_weight) {
        this.carat_weight = carat_weight;
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

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public byte[] getImage_data() {
        return image_data;
    }

    public void setImage_data(byte[] image_data) {
        this.image_data = image_data;
    }

    public MultipartFile getImg_file() {
        return img_file;
    }

    public void setImg_file(MultipartFile img_file) {
        this.img_file = img_file;
    }

    public Float getQ_price() {
        return q_price;
    }

    public void setQ_price(Float q_price) {
        this.q_price = q_price;
    }

    public Float getO_price() {
        return o_price;
    }

    public void setO_price(Float o_price) {
        this.o_price = o_price;
    }
}
