package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.User;
import org.launchcode.recipeapp.models.UserRecipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Oksana
 */
public interface UserRecipeRepository extends CrudRepository<UserRecipe, Integer> {

   List<UserRecipe> getAllByUser(User user);

   List<UserRecipe> findAllByUser(User user);

   Optional<UserRecipe> findByRecipeAndUser(Recipe recipe, User user);

   UserRecipe findByRecipe(Recipe recipe);

   void deleteByRecipe(Recipe recipe);


}
