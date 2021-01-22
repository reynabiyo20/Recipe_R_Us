package org.launchcode.recipeapp.models;


public enum FilterParameter {

        ALL("All"),
        GLUTEN_FREE ("Gluten Free"),
        VEGETARIAN("Vegetarian"),
        NUT_FREE ("Nut Free"),
        DAIRY_FREE ("Dairy Free"),
        MEAT ("Meat"),
        SEAFOOD ("Seafood");

    private String name;

        FilterParameter(String name) {
        this.name = name;
        }

    public String getName() {
        return name;
        }
}
