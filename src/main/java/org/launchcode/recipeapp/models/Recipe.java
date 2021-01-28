package org.launchcode.recipeapp.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Oksana
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "users")
@DynamicInsert
@DynamicUpdate
@Entity
public class Recipe extends AbstractEntity {

   private String name;

   @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
   private List<Ingredient> ingredients = new ArrayList<Ingredient>();

   @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
   private List<Instruction> instructions = new ArrayList<Instruction>();

   @NotNull(message = "Category required")
   private Category category;

   @ManyToMany
   private List<Tag> tags = new ArrayList<Tag>();

   private String img;

   private Double averageRating = 0.0;
   private Integer totalRatings = 0;
   private Integer numComments = 0;

   @OneToMany(mappedBy = "recipe")
   private final List<Review> reviews = new ArrayList<>();

   @OneToMany(mappedBy = "recipe", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
   @NotNull(message = "User is required")
   private List<UserRecipe> users = new ArrayList<>();

   public Recipe() {
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


   public List<Ingredient> getIngredients() {
      return ingredients;
   }

   public void setIngredients(List<Ingredient> ingredients) {
      this.ingredients = ingredients;
   }

   public List<Tag> getTags() {
      return tags;
   }

   public void setTags(List<Tag> tags) {
      this.tags = tags;
   }

   public List<Instruction> getInstructions() {
      return instructions;
   }

   public void setInstructions(List<Instruction> instructions) {
      this.instructions = instructions;
   }

   public List<Review> getReviews() {
      return reviews;
   }

   public Double getAverageRating() {
      return averageRating;
   }

   public void setAverageRating(Review newReview) {
      List<Review> reviews = getReviews();
      double sumRatings = 0.0;

         if (getTotalRatings() == 0){
               sumRatings = newReview.getRating();
         } else {
            for (int i = 0; i < getTotalRatings(); i++) {
                  sumRatings += reviews.get(i).getRating();
               }
         }
         averageRating = Double.parseDouble(String.format("%.1f", (double) sumRatings / getTotalRatings()));
      }



   public Integer getNumComments() {
      return numComments;
   }


   public Integer setNumComments(Review review) {
      if (numComments == null) {
         numComments = 0;
      }
      if (!review.getComment().isEmpty()) {
         numComments++;
      }
      return numComments;
   }

   public Integer getTotalRatings() {
      if (totalRatings == null) {
         totalRatings = 0;
      }
      return totalRatings;
   }

   public void setTotalRatings(Review review) {
      if (!getReviews().isEmpty()) {
         totalRatings++;
      }
      this.totalRatings = totalRatings;
   }

   public String getCurrentTime() {
      LocalDateTime timestampObj = LocalDateTime.now();
      DateTimeFormatter dateObj = DateTimeFormatter.ofPattern("MMMM d, yyyy");
      DateTimeFormatter timeObj = DateTimeFormatter.ofPattern("h:mm a");
      String dateTime = timestampObj.format(dateObj) + " at " + timestampObj.format(timeObj);
      return dateTime;
   }

   public LocalDateTime timestamp() {
      LocalDateTime timestampObj = LocalDateTime.now();
      return timestampObj;
   }

   public Comparator<Review> getComparator() {
      Comparator<Review> comparator = new Comparator<Review>() {
         public int compare(Review left, Review right) {
            return right.getId()- left.getId() ;
         }
      };
      return comparator;
   }
}
