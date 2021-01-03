package org.launchcode.recipeapp.controllers;

import org.launchcode.recipeapp.models.Review;
import org.launchcode.recipeapp.models.data.RecipeRepository;
import org.launchcode.recipeapp.models.Category;
import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.Tag;
import org.launchcode.recipeapp.models.data.ReviewRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("recipes")
public class RecipeController {

   private final RecipeRepository recipeRepository;

   @Autowired
   public RecipeController(RecipeRepository recipeRepository) {
      this.recipeRepository = recipeRepository;
   }

   @Autowired
   public ReviewRepository reviewRepository;

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

      return "/recipes/create";
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
      model.addAttribute("review", new Review());
      Optional<Recipe> result = recipeRepository.findById(recipeId);

      if (result.isEmpty()) { // invalid id
         model.addAttribute("title", "Invalid Recipe ID: " + recipeId);
      } else { // valid id
         Recipe recipe = result.get();
         model.addAttribute("title", recipe.getName());
         model.addAttribute("recipe", recipe);

         Integer numComments = recipe.getNumComments();
         List<Review> reviews = recipe.getReviews();

         if (reviews.isEmpty()) { // no reviews
            model.addAttribute("numRatings", "0");
            model.addAttribute("averageRating", "No ratings");
            model.addAttribute("comments", "No comments yet");
         } else { // has reviews
            model.addAttribute("averageRating", recipe.getAverageRating());
            model.addAttribute("numRatings", recipe.getReviews().size());

            if(numComments != 0){ // has comments
               model.addAttribute("comments", "Comments");
            } else if (numComments == 0 || numComments == null){ // no comments
               model.addAttribute("comments", "No comments yet");
            }
         }

      }

      return "recipes/display";
   }

   @PostMapping("display")
   public String processReviewForm(@ModelAttribute @Valid  Review newReview, Errors errors,
                                     @RequestParam Integer recipeId,
                                   Model model) {
      System.out.println(errors.hasErrors());
      Recipe recipe = recipeRepository.findById(recipeId).get();

      if (errors.hasErrors()) {
         model.addAttribute("title", recipe.getName());
         model.addAttribute("recipe", recipe);
         model.addAttribute("averageRating", recipe.getAverageRating());
         model.addAttribute("numRatings", recipe.getReviews().size());
         Integer numComments = recipe.getNumComments();

         if(numComments != 0){ // has comments
            model.addAttribute("comments", "Comments");
         } else if (numComments == 0 || numComments == null){ // no comments
            model.addAttribute("comments", "No comments yet");
         }
         return "recipes/display";
      }

      Review review = new Review(recipe, newReview.getRating(),newReview.getComment(), newReview.getName());

      review.setTimestamp();
      reviewRepository.save(review);
      recipe.setAverageRating();
      recipe.setNumComments(review);
      recipeRepository.save(recipe);

      model.addAttribute("title", recipe.getName());
      model.addAttribute("recipe", recipe);
      model.addAttribute("review", review);
      model.addAttribute("averageRating", recipe.getAverageRating());

      model.addAttribute("numRatings", recipe.getReviews().size());

      Integer numComments = recipe.getNumComments();
      if(numComments != 0){ // has comments
            model.addAttribute("comments", "Comments");
         } else if (numComments == 0 || numComments == null){ // no comments
            model.addAttribute("comments", "No comments yet");
         }
      return "recipes/display";
   }


   @GetMapping("all")
   public String getAllRecipes (Model model){

      List<Recipe> all = ((List<Recipe>) recipeRepository.findAll());

      model.addAttribute("recipes", all);

      return "recipes/all";

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

   @RequestMapping("/save/{recipeId}")
   public String saveRecipeToUser(@PathVariable Integer recipeId) {
      return "index";
   }

}

