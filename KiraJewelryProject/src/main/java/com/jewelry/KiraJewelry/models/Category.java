package com.jewelry.KiraJewelry.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int category_Id;

    @NotBlank(message = "Category name is mandatory")
    @Column(name = "category_name", nullable = false)
    private String category_Name;

    @NotNull(message = "Status is mandatory")
    @Column(name = "status", nullable = false)
    private boolean status;

    // Getters and setters
    public int getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(int category_Id) {
        this.category_Id = category_Id;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
