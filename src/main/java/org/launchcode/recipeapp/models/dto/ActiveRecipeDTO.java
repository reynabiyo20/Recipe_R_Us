package org.launchcode.recipeapp.models.dto;

import lombok.Data;
import org.launchcode.recipeapp.models.Recipe;

/**
 * @author Oksana
 */
@Data
public class ActiveRecipeDTO {

   private Recipe recipe;

   private boolean isActive;

   public Recipe getRecipe() {
      return recipe;
   }

   public boolean isActive() {
      return isActive;
   }
}
