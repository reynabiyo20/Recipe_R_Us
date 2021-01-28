package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.RecipeShoppingList;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Oksana
 */
public interface RecipeShoppingListRepository extends CrudRepository<RecipeShoppingList, Integer> {
}
