package org.launchcode.recipeapp.controllers;


import org.launchcode.recipeapp.models.*;
import org.launchcode.recipeapp.models.data.RecipeRepository;
import org.launchcode.recipeapp.models.data.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private TagRepository tagRepository;

    List<Recipe> foundRecipes = new ArrayList<>();

    // Search by KEYWORD
    @PostMapping(value = "/keywordResults")
    public String searchByKeyword(Model model, @RequestParam String keyword) {
        String lower_val = keyword.toLowerCase();
        Iterable<Recipe> recipes = recipeRepository.findAll();
        foundRecipes.clear();
        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase().contains(keyword.toLowerCase())) {
                foundRecipes.add(recipe);
            } else if (recipe.getIngredients().contains(lower_val)) {
                foundRecipes.add(recipe);
            } else if (recipe.getInstructions().contains(lower_val)) {
                foundRecipes.add(recipe);
            } else if (recipe.getCategory().toString().toLowerCase().contains(lower_val)) {
                foundRecipes.add(recipe);
            } else if (recipe.getTags().toString().toLowerCase().contains(lower_val)) {
                foundRecipes.add(recipe);
            }
        }
        model.addAttribute("recipes", foundRecipes);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", Category.values());
        model.addAttribute("sort", SortParameter.values());
        model.addAttribute("tag", tagRepository.findAll());

        return "search";
    }


    //Search by CATEGORY
    @PostMapping(value = "/categoryResults")
    public String searchRecipeByCategory(Model model, @RequestParam Category category, HttpServletRequest request) {
        String selectedValue = request.getParameter("selectedValue");
        Iterable<Recipe> recipes = recipeRepository.findAll();
        foundRecipes.clear();

        List<Tag> tags = tagRepository.findAll();
        List<Tag> filterTags = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getIsFilterable() == null) {
            } else if (tag.getIsFilterable() == true) {
                filterTags.add(tag);
            }
        }
        model.addAttribute("tag", filterTags);

        if (category == null) {
            //get all recipes
            for (Recipe recipe : recipes) {
                foundRecipes.add(recipe);
            }

            //get all recipes in selected CATEGORY search
        } else {
            for (Recipe recipe : recipes) {
                if (recipe.getCategory().name().toLowerCase().equals(category.name().toLowerCase())) {
                    foundRecipes.add(recipe);

                }
            }
        }

        //render found recipes
        model.addAttribute("recipes", foundRecipes);
        model.addAttribute("categories", Category.values());
        model.addAttribute("category", category);
        model.addAttribute("sort", SortParameter.values());
        model.addAttribute("category", category);

        return "search";
    }

    //SORTING
    @PostMapping(value = "/sort")
    public String sortSearchResults(@RequestParam SortParameter sortParameter, @RequestParam Category category, Model model) {
//        Iterable<Recipe> recipes = foundRecipes;

        List<Tag> tags = tagRepository.findAll();
        List<Tag> filterTags = new ArrayList<>();
        for (Tag tag : tags) {
            if(tag.getIsFilterable() == null){}
            else if (tag.getIsFilterable()) {
                filterTags.add(tag);
            }
        }
        model.addAttribute("tag", filterTags);


        //If selected sort is NAME ASCENDING
        if ((sortParameter.getName().equals(SortParameter.NAME_ASCENDING.getName()))) {
            Collections.sort(foundRecipes, new Recipe.SortByNameAsc());

            //If selected sort is NAME DESCENDING
        } else if ((sortParameter.getName().equals(SortParameter.NAME_DESCENDING.getName()))) {
            Collections.sort(foundRecipes, new Recipe.SortByNameDesc());

            //if selected sort is ASCENDING RATING
        } else if ((sortParameter.getName().equals(SortParameter.RATING_ASCENDING.getName()))) {
            Collections.sort(foundRecipes, new Recipe.SortByRatingAsc());

            //if selected sort is DESCENDING RATING
        } else if ((sortParameter.getName().equals(SortParameter.RATING_DESCENDING.getName()))) {
            Collections.sort(foundRecipes, new Recipe.SortByRatingDsc());

        }
        //render found recipes
        model.addAttribute("recipes", foundRecipes);
        model.addAttribute("categories", Category.values());
        model.addAttribute("category", category);
        model.addAttribute("sort", SortParameter.values());

        return "search";
    }

    @PostMapping(value = "/filter")
    public String filterResults(@RequestParam List<Integer> tagId, @RequestParam Category category, Model model) {
        model.addAttribute("category", category);

        // store selected filters in an arrayList
        List<Tag> selectedTags = new ArrayList<>();
        for (Integer aTagId : tagId) {
            selectedTags.add(tagRepository.findById(aTagId).get());
        }

        // filter checkboxes
        List<Tag> allTags = tagRepository.findAll();
        List<Tag> filters = new ArrayList<>();
        for (Tag aTag : allTags) {
            if (aTag.getIsFilterable() == null) {
            } else if (aTag.getIsFilterable() == true) {
                filters.add(aTag);
                model.addAttribute("tag", filters);
            }
        }

        // find and store recipes with the selected tag
        Iterable<Recipe> recipes = foundRecipes;
        List<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            for (Tag recipeTag : recipe.getTags()) {
                for (Tag tag : selectedTags) {
                    if (recipeTag.getId() == tag.getId()) {
                        if(!filteredRecipes.contains(recipe)) {
                            filteredRecipes.add(recipe);
                        }
                    }
                }
            }
        }

        //render filtered recipes
        model.addAttribute("recipes", filteredRecipes);
        model.addAttribute("categories", Category.values());
        model.addAttribute("sort", SortParameter.values());

        return "search";
    }
}