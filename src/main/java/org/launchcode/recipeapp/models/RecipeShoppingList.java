package org.launchcode.recipeapp.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Oksana
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class RecipeShoppingList extends AbstractEntity {

   @ManyToOne(targetEntity = Recipe.class,
           fetch = FetchType.LAZY,
           cascade = {CascadeType.MERGE})
   @JoinColumn(name = "recipe_id",
           referencedColumnName = "id",
           foreignKey = @ForeignKey(name = "FK_RECIPE_SHOPPING_LIST"))
   private Recipe recipe;

   @ManyToOne(targetEntity = ShoppingList.class,
           fetch = FetchType.LAZY,
           cascade = {CascadeType.MERGE})
   @JoinColumn(name = "shopping_list_id",
           referencedColumnName = "id",
           foreignKey = @ForeignKey(name = "FK_SHOPPING_LIST_RECIPE"))
   private ShoppingList shoppingList;

   private int portions;

   public Recipe getRecipe() {
      return recipe;
   }

   public void setRecipe(Recipe recipe) {
      this.recipe = recipe;
   }

   public ShoppingList getShoppingList() {
      return shoppingList;
   }

   public void setShoppingList(ShoppingList shoppingList) {
      this.shoppingList = shoppingList;
   }

   public int getPortions() {
      return portions;
   }

   public void setPortions(int portions) {
      this.portions = portions;
   }
}
