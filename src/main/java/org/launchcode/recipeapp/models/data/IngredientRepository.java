package org.launchcode.recipeapp.models.data;

import org.launchcode.recipeapp.models.Ingredient;
import org.launchcode.recipeapp.models.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    Ingredient findByIngredient (String ingredient);
    List<Ingredient> findByRecipeId(Integer recipeId);
}