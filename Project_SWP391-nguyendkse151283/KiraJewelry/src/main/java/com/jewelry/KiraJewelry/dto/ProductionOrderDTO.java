package com.jewelry.KiraJewelry.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionOrderDTO {
    private String category;
    private int productSize;
    private String material_Name;
    private String material_Color;
    private int material_Weight;
    private String gemstone_Name;
    private String gem_Color;
    private int gem_Weight;
    private String description;
}
