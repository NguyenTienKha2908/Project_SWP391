package com.jewelry.KiraJewelry.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductMaterialId implements Serializable {
    @Column(name = "Product_Id")
    private int product_Id;

    @Column(name = "Material_Id")
    private int material_Id;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductMaterialId that = (ProductMaterialId) o;
        return product_Id == that.product_Id && material_Id == that.material_Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_Id, material_Id);
    }
}
