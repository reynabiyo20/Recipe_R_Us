package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.User;
import org.launchcode.recipeapp.models.UserRecipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Oksana
 */
public interface UserRecipeRepository extends CrudRepository<UserRecipe, Integer> {

   List<UserRecipe> getAllByUser(User user);

}
