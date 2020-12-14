package org.launchcode.recipeapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends AbstractEntity {

   private String name;

   @NotNull(message = "Ingredients required")
   private String ingredients;

   private String directions;

   @NotNull(message = "Category required")
   private Category category;

   private Tag tag;

   private String img;
   private Double averageRating;

   @OneToMany(mappedBy = "recipe")
   private final List<Review> reviews = new ArrayList<>();

   @OneToMany(mappedBy = "recipe", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
   @NotNull(message = "User is required")
   private List<UserRecipe> users = new ArrayList<>();

   public Recipe() {
   }


   public double calculateAverageRating(){
      int numRatings = reviews.size();
      int sumRatings = 0;

      for( int i =0; i < numRatings; i++){
        int rating = reviews.get(i).getRating();
         sumRatings += rating;
      }
      double average = sumRatings / numRatings;
      averageRating = average;
      return averageRating;
   }



   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }


   public Category getCategory() {
      return category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public List<UserRecipe> getUsers() {
      return users;
   }

   public void setUsers(List<UserRecipe> users) {
      this.users = users;
   }

   public String getImg() {
      return img;
   }

   public void setImg(String img) {
      this.img = img;
   }

   public String getIngredients() {
      return ingredients;
   }

   public void setIngredients(String ingredients) {
      this.ingredients = ingredients;
   }

   public String getDirections() {
      return directions;
   }

   public void setDirections(String directions) {
      this.directions = directions;
   }

   public Tag getTag() {
      return tag;
   }

   public void setTag(Tag tag) {
      this.tag = tag;
   }

   public Double getAverageRating() {
      return averageRating;
   }

   public void setAverageRating(Double averageRating) {
      this.averageRating = averageRating;
   }

   public List<Review> getReviews() {
      return reviews;
   }

   @Override
   public String toString() {
      return "Recipe{" +
              "name='" + name + '\'' +
              ", ingredients='" + ingredients + '\'' +
              ", directions='" + directions + '\'' +
              ", category=" + category +
              ", tag=" + tag +
              ", img='" + img + '\'' +
              ", averageRating=" + averageRating +
              ", reviews=" + reviews +
              ", users=" + users +
              '}';
   }
}
