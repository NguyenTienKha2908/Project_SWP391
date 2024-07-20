package com.example.CustomerView.entity;

import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
