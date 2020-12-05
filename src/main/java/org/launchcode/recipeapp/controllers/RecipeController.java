package org.launchcode.recipeapp.controllers;

import org.launchcode.recipeapp.data.RecipeRepository;
import org.launchcode.recipeapp.models.Category;
import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

   @GetMapping("edit/{recipeId}")
   public String displayEditForm(Model model, @PathVariable int recipeId) {

      Category[] categories = Category.values();
      Tag[] tags = Tag.values();
      Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
      if (recipeOpt.isPresent()) {
         Recipe recipe = recipeOpt.get();
         model.addAttribute("recipe", recipe);
         model.addAttribute("title", "Edit recipe " + recipe.getName());
         model.addAttribute("recipeId", recipe.getId());
      } else {
         model.addAttribute("recipe", new Recipe());
      }
      model.addAttribute("categories", categories);
      model.addAttribute("tags", tags);

      return "recipes/edit";

   }

   @PostMapping("edit")
   public String processEditForm(Integer recipeId, @ModelAttribute @Valid Recipe newRecipe,
                                 Errors errors, Model model, RedirectAttributes redirectAttrs) {
      if (errors.hasErrors()) {
         model.addAttribute("title", "Edit Recipe");
         return "recipes/edit";
      }
      Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
      if (recipeOpt.isPresent()) {
         Recipe recipe = recipeOpt.get();
         recipe.setCategory(newRecipe.getCategory());
         recipe.setDirections(newRecipe.getDirections());
         recipe.setImg(newRecipe.getImg());
         recipe.setIngredients(newRecipe.getIngredients());
         recipe.setName(newRecipe.getName());
         recipe.setTag(newRecipe.getTag());


         Recipe savedRecipe = recipeRepository.save(recipe);
         Iterable<Recipe> recipes = recipeRepository.findAll();

         redirectAttrs.addAttribute("recipes", recipes);

      }
      return "redirect:";

   }

   @RequestMapping("/delete/{recipeId}")
   public String handleDeleteUser(@PathVariable Integer recipeId) {
      Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
      if (recipeOpt.isPresent()) {
         recipeRepository.deleteById(recipeId);
      }

      return "redirect:/recipes";
   }

}
