package org.launchcode.recipeapp.models;

public enum SortParameter {
    NAME_ASCENDING ("Recipe Name: A-Z"),
    NAME_DESCENDING ("Recipe Name: Z-A"),
    RATING_ASCENDING ("Average Rating: High-Low"),
    RATING_DESCENDING ("Average Rating: Low-High"),
    TIME_ASCENDING ("Total Time: High-Low"),
    TIME_DESCENDING ("Total Time: Low-High");


    private String name;

    SortParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
