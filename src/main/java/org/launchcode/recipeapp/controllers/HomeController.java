package org.launchcode.recipeapp.controllers;


import org.launchcode.recipeapp.models.data.RecipeRepository;
import org.launchcode.recipeapp.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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

   @GetMapping("")
   public String home(Model model) {
      model.addAttribute("title", "Saint Louis Best Recipes");

      List<Recipe> recipes = new ArrayList<>();
      Iterable<Recipe> all = recipeRepository.findAll();
      all.forEach(recipes::add);

      model.addAttribute("recipes", recipes);

      return "index";
   }


}
