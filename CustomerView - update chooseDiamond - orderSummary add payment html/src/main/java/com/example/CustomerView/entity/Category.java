package com.example.CustomerView.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Category_Id")
    private int category_Id;

    @NotBlank(message = "Category name is mandatory")
    @Column(name = "Category_Name", nullable = false)
    private String category_Name;

    @NotNull(message = "Status is mandatory")
    @Column(name = "Status", nullable = false)
    private boolean status;

    @Column(name = "Img_Url", nullable = true)
    private String img_Url;

    @Lob
    @Column(name = "Image_Data", nullable = true)
    private byte[] imageData;
}
