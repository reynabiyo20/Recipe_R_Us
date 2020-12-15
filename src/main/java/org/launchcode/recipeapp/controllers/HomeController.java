package org.launchcode.recipeapp.controllers;


import org.launchcode.recipeapp.models.User;
import org.launchcode.recipeapp.models.UserRecipe;
import org.launchcode.recipeapp.models.data.RecipeRepository;
import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.data.UserRecipeRepository;
import org.launchcode.recipeapp.models.dto.ActiveRecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oksana
 */
@Controller
public class HomeController {

   private final RecipeRepository recipeRepository;

   private final UserRecipeRepository userRecipeRepository;

   @Autowired
   public HomeController(RecipeRepository recipeRepository, UserRecipeRepository userRecipeRepository) {
      this.recipeRepository = recipeRepository;
      this.userRecipeRepository = userRecipeRepository;
   }

   @GetMapping("")
   public String home(Model model, HttpServletRequest request) {
      User user = (User) request.getSession().getAttribute("user");
      model.addAttribute("title", "Saint Louis Best Recipes");

      List<ActiveRecipeDTO> recipes = new ArrayList<>();
      Iterable<Recipe> all = recipeRepository.findAll();

      List<UserRecipe> allByUser = userRecipeRepository.getAllByUser(user);


      for (Recipe recipe : all) {
         ActiveRecipeDTO activeRecipeDTO = new ActiveRecipeDTO();
         activeRecipeDTO.setRecipe(recipe);
         boolean isActive = allByUser.stream()
                 .anyMatch(recipeByUser -> recipeByUser.getRecipe().equals(recipe));
         activeRecipeDTO.setActive(isActive);
         recipes.add(activeRecipeDTO);
      }


      model.addAttribute("recipes", recipes);

      return "index";
   }


}
