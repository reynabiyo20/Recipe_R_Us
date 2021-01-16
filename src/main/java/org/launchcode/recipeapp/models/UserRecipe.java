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
           cascade = {CascadeType.MERGE})
   @JoinColumn(name = "user_id",
           referencedColumnName = "id",
           foreignKey = @ForeignKey(name = "FK_USER"))
   private User user;

   @ManyToOne(targetEntity = Recipe.class,
           fetch = FetchType.LAZY,
           cascade = {CascadeType.MERGE})
   @JoinColumn(name = "recipe_id",
           referencedColumnName = "id",
           foreignKey = @ForeignKey(name = "FK_USER_RECIPE"))
   private Recipe recipe;

   @Override
   public String toString() {
      return "UserRecipe{" +
              "user=" + user +
              ", recipe=" + recipe +
              '}';
   }
}
