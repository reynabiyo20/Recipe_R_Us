package org.launchcode.recipeapp.controllers;


import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.data.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Oksana
 */
@Controller
public class HomeController {

   private final RecipeRepository recipeRepository;

   @Autowired
   public HomeController(RecipeRepository recipeRepository) {
      this.recipeRepository = recipeRepository;
   }

   @GetMapping("/home")
   public String home(Model model) {
      Iterable<Recipe> all = recipeRepository.findAll();
      model.addAttribute("recipes", all);
      model.addAttribute("title", "Saint Louis Best Recipes");

      return "index";
   }


}
