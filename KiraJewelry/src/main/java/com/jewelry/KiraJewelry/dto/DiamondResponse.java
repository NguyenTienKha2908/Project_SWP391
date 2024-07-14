package com.jewelry.KiraJewelry.dto;

import com.jewelry.KiraJewelry.models.Diamond;

public class DiamondResponse {
    private Diamond diamond;
    private String imageUrl;

    // Constructors
    public DiamondResponse(Diamond diamond, String imageUrl) {
        this.diamond = diamond;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    public Diamond getDiamond() {
        return diamond;
    }

    public void setDiamond(Diamond diamond) {
        this.diamond = diamond;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
