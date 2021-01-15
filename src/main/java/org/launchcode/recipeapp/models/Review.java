package org.launchcode.recipeapp.models;

import org.launchcode.recipeapp.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Review extends AbstractEntity{

    @ManyToOne
    private Recipe recipe;

    @ManyToOne(targetEntity = User.class,
        fetch = FetchType.LAZY,
        cascade = {CascadeType.MERGE, CascadeType.REMOVE})

    private User user;

    private String username;

    @Size(max=250, message = "Comments must be under 250 characters")
    private String comment;

    @NotNull(message = "Rating is required")
    private Integer rating;

    private String timestamp;

    public Review(Recipe recipe, Integer rating, String comment, User user, String username, String timestamp) {
        this();
        this.recipe = recipe;
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.username = username;
        this.timestamp = timestamp;
    }

    public Review() {
    }

    // getters and setters
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void updateCalculations(Recipe recipe, Review review){
        recipe.setAverageRating();
        recipe.setTotalRatings(review);
        recipe.setNumComments(review);
    }

}
