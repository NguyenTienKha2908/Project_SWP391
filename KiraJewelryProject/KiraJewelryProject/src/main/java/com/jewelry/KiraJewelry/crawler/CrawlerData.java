package com.jewelry.KiraJewelry.crawler;

import java.util.Date;

import com.jewelry.KiraJewelry.validation.PastOrPresentDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Material_Price_List_Test")
public class CrawlerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Min(value = 0, message = "The price must be greater than or equal to 0")
    @Column(name = "price", nullable = false)
    private double price;

    @PastOrPresentDate
    @Column(name = "eff_date", nullable = false)
    private Date eff_Date;

    @Column(name = "material_id", nullable = false)
    private int material_Id;
}
