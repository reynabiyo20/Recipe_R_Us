package org.launchcode.recipeapp.controllers;

import org.launchcode.recipeapp.models.data.RecipeRepository;
import org.launchcode.recipeapp.models.Category;
import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Oksana
 */
@Controller
@RequestMapping("recipes")
public class RecipeController {

   private final RecipeRepository recipeRepository;

   @Autowired
   public RecipeController(RecipeRepository recipeRepository) {
      this.recipeRepository = recipeRepository;
   }

   @GetMapping
   public String getListOfRecipes(Model model) {
      Iterable<Recipe> recipes = recipeRepository.findAll();
      model.addAttribute("recipes", recipes);

      return "recipes/index";
   }

   @GetMapping("create")
   public String createRecipe(Model model) {
      Category[] categories = Category.values();
      Tag[] tags = Tag.values();

      model.addAttribute("title", "Create Recipe");
      model.addAttribute("recipe", new Recipe());
      model.addAttribute("categories", categories);
      model.addAttribute("tags", tags);

      return "recipes/create";
   }

   @PostMapping("create")
   public String createRecipe(@ModelAttribute @Valid Recipe newRecipe,
                              @ModelAttribute @Valid String newCategory,
                              Errors errors, Model model, RedirectAttributes redirectAttrs) {

      if (errors.hasErrors()) {
         model.addAttribute("title", "Create Recipe");
         return "recipes/create";
      }

      Recipe recipe = recipeRepository.save(newRecipe);
      redirectAttrs.addAttribute("recipeId", recipe.getId());

      return "redirect:/recipes/display";
   }


   @GetMapping("display")
   public String displayRecipe(@RequestParam Integer recipeId, Model model) {

      Optional<Recipe> result = recipeRepository.findById(recipeId);

      if (result.isEmpty()) {
         model.addAttribute("title", "Invalid Recipe ID: " + recipeId);
      } else {
         Recipe recipe = result.get();

         model.addAttribute("title", recipe.getName());
         model.addAttribute("recipe", recipe);
      }

      return "recipes/display";
   }

}
