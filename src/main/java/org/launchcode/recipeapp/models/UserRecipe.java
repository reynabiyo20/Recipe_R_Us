package org.launchcode.recipeapp.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Oksana
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table
public class UserRecipe extends AbstractEntity {

   @ManyToOne(targetEntity = User.class,
           fetch = FetchType.LAZY,
           cascade = {CascadeType.MERGE, CascadeType.REMOVE})
   @JoinColumn(name = "user_id",
           referencedColumnName = "id",
           foreignKey = @ForeignKey(name = "FK_USER"))
   @NotNull(message = "")
   private User user;

   @ManyToOne(targetEntity = Recipe.class,
           fetch = FetchType.LAZY,
           cascade = {CascadeType.MERGE, CascadeType.REMOVE})
   @JoinColumn(name = "recipe_id",
           referencedColumnName = "id",
           foreignKey = @ForeignKey(name = "FK_USER_RECIPE"))
   @NotNull(message = "")
   private Recipe recipe;

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Recipe getRecipe() {
      return recipe;
   }

   public void setRecipe(Recipe recipe) {
      this.recipe = recipe;
   }
}
