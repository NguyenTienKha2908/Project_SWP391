package com.jewelry.KiraJewelry.models;


import com.jewelry.KiraJewelry.validation.PastOrPresentDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private int blog_Id;

    @NotBlank(message = "Blog title is mandatory")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "blog content is mandatory")
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull(message = "Date is mandatory")
    @PastOrPresentDate(message = "Date must be in the past or present")
    @Column(name = "date", nullable = false)
    private java.sql.Date date;

    @NotBlank(message = "Employee ID is mandatory")
    @Column(name = "employee_Id", nullable = false)
    private String employee_Id;

    @NotNull(message = "Status is mandatory")
    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "img_url", nullable = true)
    private String img_Url;

    @Lob
    @Column(name = "image_data", nullable = true)
    private byte[] imageData;

    // Getters and setters
    public int getBlog_Id() {
        return blog_Id;
    }

    public void setBlog_Id(int blog_Id) {
        this.blog_Id = blog_Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getEmployee_Id() {
        return employee_Id;
    }

    public void setEmployee_Id(String employee_Id) {
        this.employee_Id = employee_Id;
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

