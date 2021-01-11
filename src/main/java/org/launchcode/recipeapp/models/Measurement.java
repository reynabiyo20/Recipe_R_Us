package org.launchcode.recipeapp.models;

public enum Measurement {
    CUP ("Cup"),
    TABLESPOON ("Tablespoon"),
    TEASPOON ("Teaspoon"),
    GRAM ("Gram"),
    OUNCE ("Ounce"),
    ML ("Ml"),
    LITER ("Liter"),
    INCH ("Inch"),
    POUND ("Pound"),
    KILOGRAM("Kilogram"),
    EACH ("Each"),
    FLUID_OUNCE ("Fluid Ounce"),
    PINT ("Pint"),
    QUART ("Quart"),
    GALLON ("Gallon");

    private String name;

    Measurement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
