package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.ShoppingList;
import org.launchcode.recipeapp.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Oksana
 */
public interface ShoppingListRepository extends CrudRepository<ShoppingList, Integer> {

   Optional<ShoppingList> findByUserAndActive(User user, boolean active);

}
