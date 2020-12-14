package org.launchcode.recipeapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Review extends AbstractEntity{

    @ManyToOne
    private Recipe recipe;

    private Integer rating;
    private String comment;

    public Review() {
    }

    public Review(Recipe recipe, Integer rating, String comment) {
        this.recipe = recipe;
        this.rating = rating;
        this.comment = comment;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "recipe=" + recipe +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
