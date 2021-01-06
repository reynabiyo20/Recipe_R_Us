package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    List<Ingredient> findByRecipeId(Integer recipeId);
}