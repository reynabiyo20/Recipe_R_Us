package org.launchcode.recipeapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Instruction extends AbstractEntity{

    @ManyToOne
    private Recipe recipe;

    private String step;

    public Instruction(String step) {
        this.step = step;
    }

    public Instruction() {

    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}