package org.launchcode.recipeapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ingredient extends AbstractEntity{

    @ManyToOne
    private Recipe recipe;

    private String measurement;

    private double quantity;

    private String ingredient;

    public Ingredient(String ingredient, double quantity, String measurement) {
        this.ingredient = ingredient;
        this.measurement = measurement;
        this.quantity = quantity;
    }

    public Ingredient() {

    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}