package com.amadeus.nutrasoft.calc;

import static com.amadeus.nutrasoft.commons.Utils.round;

public enum Macronutrient {
    PRO(4), CHO(4), FAT(9);

    /**
     * Energy density is the amount of energy per weight of food (kcal/g)
     */
    private int density;

    Macronutrient(int density) {
        this.density = density;
    }

    public double weight(double energ) {
        return round(energ / density, 1);
    }

    public double energ(double weight) {
        return weight * density;
    }
}
