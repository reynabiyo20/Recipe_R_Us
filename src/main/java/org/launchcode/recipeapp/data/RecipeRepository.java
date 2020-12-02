package org.launchcode.recipeapp.data;

import org.launchcode.recipeapp.models.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Oksana
 */
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
   Recipe findByName (String name);
}
