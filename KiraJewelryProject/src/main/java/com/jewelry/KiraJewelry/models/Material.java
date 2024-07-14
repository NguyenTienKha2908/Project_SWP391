package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "material")
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

    @Column(name = "img_url", nullable = true)
    private String img_Url;

    @Column(name = "status", nullable = false)
    private int status; // 1 for active, 0 for inactive


}
