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

    private int material_Id;
    private int gem_id;
    private double material_amount;
    private double diamond_amount;
    private double side_material_cost;
    private double side_gem_cost;
    private double production_Amount;
    private double total_amount;

    //Added url for img
    private String image_url;

    // Default constructor
    public ProductionOrderDTO() {
    }

    // Parameterized constructor
    public ProductionOrderDTO(
            int material_Id,
            int gem_id,
            double material_amount,
            double diamond_amount,
            double side_material_cost,
            double side_gem_cost,
            double production_Amount,
            double total_amount) {
        this.material_Id = material_Id;
        this.gem_id = gem_id;
        this.material_amount = material_amount;
        this.diamond_amount = diamond_amount;
        this.side_material_cost = side_material_cost;
        this.side_gem_cost = side_gem_cost;
        this.production_Amount = production_Amount;
        this.total_amount = total_amount;
    }

    // Parameterized constructor
    public ProductionOrderDTO(String category, int productSize, String material_Name, String material_Color,
            int material_Weight,
            String gemstone_Name, String gem_Color, int gem_Weight, String description, String img_url) {
        this.category = category;
        this.productSize = productSize;
        this.material_Name = material_Name;
        this.material_Color = material_Color;
        this.material_Weight = material_Weight;
        this.gemstone_Name = gemstone_Name;
        this.gem_Color = gem_Color;
        this.gem_Weight = gem_Weight;
        this.description = description;
        this.image_url = img_url;

    }
}
