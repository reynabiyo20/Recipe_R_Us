package org.launchcode.recipeapp.controllers;

import org.launchcode.recipeapp.models.Category;
import org.launchcode.recipeapp.models.Ingredient;
import org.launchcode.recipeapp.models.Instruction;
import org.launchcode.recipeapp.models.Measurement;
import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.Review;
import org.launchcode.recipeapp.models.Tag;
import org.launchcode.recipeapp.models.User;
import org.launchcode.recipeapp.models.UserRecipe;
import org.launchcode.recipeapp.models.data.IngredientRepository;
import org.launchcode.recipeapp.models.data.InstructionRepository;
import org.launchcode.recipeapp.models.data.RecipeRepository;
import org.launchcode.recipeapp.models.data.ReviewRepository;
import org.launchcode.recipeapp.models.data.UserRecipeRepository;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Oksana
 */
@Controller
@RequestMapping("recipes")
public class RecipeController {

   private final org.launchcode.recipeapp.models.data.RecipeRepository recipeRepository;
   private final IngredientRepository ingredientRepository;
   private final InstructionRepository instructionRepository;
   private final ReviewRepository reviewRepository;
   private final UserRecipeRepository userRecipeRepository;

   @Autowired
   public RecipeController(RecipeRepository recipeRepository,
                           IngredientRepository ingredientRepository,
                           InstructionRepository instructionRepository,
                           UserRecipeRepository userRecipeRepository,
                           ReviewRepository reviewRepository) {
      this.recipeRepository = recipeRepository;
      this.userRecipeRepository = userRecipeRepository;
      this.ingredientRepository = ingredientRepository;
      this.instructionRepository = instructionRepository;
      this.reviewRepository = reviewRepository;
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
      Measurement[] measurements = Measurement.values();
      Tag[] tags = Tag.values();

      model.addAttribute("title", "Create Recipe");
      model.addAttribute("recipe", new Recipe());
      model.addAttribute("categories", categories);
      model.addAttribute("tags", tags);
      model.addAttribute("measurements", measurements);

      return "recipes/create";
   }

   @PostMapping("create")
   public String createRecipe(HttpServletRequest request, @ModelAttribute Recipe newRecipe,
                              @ModelAttribute @Valid String newCategory,
                              Errors errors, Model model, RedirectAttributes redirectAttrs) {

      if (errors.hasErrors()) {
         model.addAttribute("title", "Create Recipe");
         return "recipes/create";
      }

      String[] ingredients = request.getParameterValues("ingredient");
      String[] instructions = request.getParameterValues("instruction");
      String[] measurements = request.getParameterValues("measurement");
      String[] quantity = request.getParameterValues("quantity");

      List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
      List<Instruction> instructionsList = new ArrayList<Instruction>();

      Recipe recipe = recipeRepository.save(newRecipe);


      for (int i = 0; i < ingredients.length; i++) {
         Ingredient newIngredient = new Ingredient(ingredients[i], Double.parseDouble(quantity[i]), measurements[i]);
         newIngredient.setRecipe(recipe);
         ingredientsList.add(newIngredient);
         ingredientRepository.save(newIngredient);
      }
      for (int i = 0; i < instructions.length; i++) {
         Instruction newInstruction = new Instruction(instructions[i]);
         newInstruction.setRecipe(recipe);
         instructionsList.add(newInstruction);
         instructionRepository.save(newInstruction);
      }
      redirectAttrs.addAttribute("recipeId", recipe.getId());

      return "redirect:/recipes/display";
   }


   @GetMapping("display")
   public String displayRecipe(@RequestParam Integer recipeId, Model model, HttpServletRequest request) {
      model.addAttribute("review", new Review());
      Optional<Recipe> result = recipeRepository.findById(recipeId);

      if (result.isEmpty()) {
         model.addAttribute("title", "Invalid Recipe ID: " + recipeId);
      } else {
         Recipe recipe = result.get();
         model.addAttribute("title", recipe.getName());
         model.addAttribute("recipe", recipe);
         User sessionUser = (User) request.getSession().getAttribute("user");

         Optional<UserRecipe> recipeByUserOptional = userRecipeRepository.findByRecipeAndUser(recipe,sessionUser);

         boolean isFavourite;
         if (recipeByUserOptional.isPresent()) {
            isFavourite = true;
            model.addAttribute("title1", "This recipe has already been added to your profile ");
         } else {
            isFavourite = false;
         }
         model.addAttribute("isFavourite", isFavourite);
      }

      return "recipes/display";
   }

   @PostMapping("display")
   public String processReviewForm(@ModelAttribute @Valid  Review newReview, Errors errors,
                                   @RequestParam Integer recipeId,
                                   Model model, HttpServletRequest request) {
      Recipe recipe = recipeRepository.findById(recipeId).get();

      if (errors.hasErrors()) {
         model.addAttribute("title", recipe.getName());
         model.addAttribute("recipe", recipe);

         return "redirect:/recipes/display?recipeId="+recipeId;
      }

      // add review & update recipe calculations
      User sessionUser = (User) request.getSession().getAttribute("user");
      Review review = new Review(recipe, newReview.getRating(), newReview.getComment(), sessionUser, sessionUser.getUsername(), recipe.getCurrentTime());
      reviewRepository.save(review);
      review.updateCalculations(recipe,review);
      recipeRepository.save(recipe);

      model.addAttribute("title", recipe.getName());
      model.addAttribute("recipe", recipe);
      model.addAttribute("review", review);

      return "redirect:/recipes/display?recipeId="+recipeId;
   }

   @GetMapping("all")
   public String getAllRecipes (Model model){

      List<Recipe> all = ((List<Recipe>) recipeRepository.findAll());

      model.addAttribute("recipes", all);

      return "redirect:/recipes";

   }


   @GetMapping("edit/{recipeId}")
   public String displayEditForm(Model model, @PathVariable int recipeId) {

      Category[] categories = Category.values();
      Measurement[] measurements = Measurement.values();
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
      model.addAttribute("measurements", measurements);
      model.addAttribute("tags", tags);

      return "recipes/edit";

   }

   @PostMapping("edit")
   public String processEditForm(HttpServletRequest request, Integer recipeId, @ModelAttribute Recipe newRecipe,
                                 Errors errors, Model model, RedirectAttributes redirectAttrs) {
      if (errors.hasErrors()) {
         model.addAttribute("title", "Edit Recipe");
         return "recipes/edit";
      }

      String[] ingredients = request.getParameterValues("ingredient");
      String[] instructions = request.getParameterValues("instruction");
      String[] measurements = request.getParameterValues("measurement");
      String[] quantity = request.getParameterValues("quantity");

      List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
      List<Instruction> instructionsList = new ArrayList<Instruction>();


      Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
      if (recipeOpt.isPresent()) {
         Recipe recipe = recipeOpt.get();

         List<Ingredient> ingredientsToDelete = ingredientRepository.findByRecipeId(recipe.getId());
         for (int i = 0; i < ingredientsToDelete.size(); i++) {
            ingredientRepository.delete(ingredientsToDelete.get(i));
         }
         List<Instruction> instructionsToDelete = instructionRepository.findByRecipeId(recipe.getId());
         for (int i = 0; i < instructionsToDelete.size(); i++) {
            instructionRepository.delete(instructionsToDelete.get(i));
         }

         recipe.setCategory(newRecipe.getCategory());
         recipe.setImg(newRecipe.getImg());
         recipe.setName(newRecipe.getName());
         recipe.setTag(newRecipe.getTag());

         for (int i = 0; i < ingredients.length; i++) {
            Ingredient newIngredient = new Ingredient(ingredients[i], Double.parseDouble(quantity[i]), measurements[i]);
            newIngredient.setRecipe(recipe);
            ingredientsList.add(newIngredient);
            ingredientRepository.save(newIngredient);
         }

         for (int i = 0; i < instructions.length; i++) {
            Instruction newInstruction = new Instruction(instructions[i]);
            newInstruction.setRecipe(recipe);
            instructionsList.add(newInstruction);
            instructionRepository.save(newInstruction);
         }


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

   @RequestMapping("/save/{recipeId}")
   public String saveRecipeToUser(@PathVariable Integer recipeId) {
      return "index";
   }

}
