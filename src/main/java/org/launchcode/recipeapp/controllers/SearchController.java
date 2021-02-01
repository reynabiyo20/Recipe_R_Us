package org.launchcode.recipeapp.controllers;


import org.launchcode.recipeapp.models.*;
import org.launchcode.recipeapp.models.data.IngredientRepository;
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

    @Autowired
    private IngredientRepository ingredientRepository;

    List<Recipe> foundRecipes = new ArrayList<>();

    List<Tag> selectedTags = new ArrayList<>();

    // Search by KEYWORD
    @PostMapping(value = "/keywordResults")
    public String searchByKeyword(Model model, @RequestParam String keyword) {
        String lowerVal = keyword.toLowerCase();
        Iterable<Recipe> recipes = recipeRepository.findAll();
        foundRecipes.clear();
        Tag tagRec = (Tag) tagRepository.findByName(lowerVal);
        Ingredient ingRec = (Ingredient) ingredientRepository.findByIngredient(lowerVal);


        if (tagRec != null) {
            foundRecipes = ((List<Recipe>) tagRec.getRecipes());
        }
        if (ingRec != null) {
            foundRecipes.add((Recipe) ingRec.getRecipe());
        }
        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase().contains(lowerVal)) {
                if (!foundRecipes.contains(recipe))
                    foundRecipes.add(recipe);
            } else if (recipe.getCategory().toString().toLowerCase().contains(lowerVal)) {
                if (!foundRecipes.contains(recipe))
                    foundRecipes.add(recipe);
            }
            List<Tag> tags = tagRepository.findAll();
            List<Tag> filterTags = new ArrayList<>();
            for (Tag tag : tags) {
                if (tag.getIsFilterable() == null) {
                } else if (tag.getIsFilterable()) {
                    filterTags.add(tag);
                }
            }
                    model.addAttribute("tag", filterTags);
        }
                    model.addAttribute("recipes", foundRecipes);
                    model.addAttribute("keyword", keyword);
                    model.addAttribute("categories", Category.values());
                    model.addAttribute("sort", SortParameter.values());

                    return "search";
    }



//        if(lowerVal.contains(" ")) {
//
//            String[] str = lowerVal.split(" ");
//            List<String> list;
//            list = Arrays.asList(str);
//            for (String word : list) {
//                Tag tagRec1 = (Tag) tagRepository.findByName(word);
//                Ingredient ingRec1 = (Ingredient) ingredientRepository.findAll();
//                if (tagRec != null) {
//                    foundRecipes.add((Recipe) tagRec1.getRecipes());
//                }
//                if (ingRec != null) {
//                    foundRecipes.add((Recipe) ingRec1.getRecipe());
//                }
//
//                for (Recipe recipe1 : recipes) {
//                    if (recipe1.getName().toLowerCase().contains(word)) {
//                        if (!foundRecipes.contains(recipe1))
//                            foundRecipes.add(recipe1);
//                    } else if (recipe1.getCategory().toString().toLowerCase().contains(word)) {
//                        if (!foundRecipes.contains(recipe1))
//                            foundRecipes.add(recipe1);
//                    }
//                }
//            }
//        }




    //Search by CATEGORY
    @PostMapping(value = "/categoryResults")
    public String searchRecipeByCategory(Model model, @RequestParam Category category, HttpServletRequest request) {
        String selectedValue = request.getParameter("selectedValue");
        Iterable<Recipe> recipes = recipeRepository.findAll();
        foundRecipes.clear();
        selectedTags.clear();

            if (category == null) {
                //get all recipes
                for(Recipe recipe : recipes) {
                    foundRecipes.add(recipe);
                }
                } else {
                //get all recipes in selected CATEGORY search
                    for (Recipe recipe : recipes) {
                        if (recipe.getCategory().name().toLowerCase().equals(category.name().toLowerCase())) {
                            foundRecipes.add(recipe);
                        }
                    }
                }

            List<Tag> tags = tagRepository.findAll();
            List<Tag> filterTags = new ArrayList<>();
            for (Tag tag : tags) {
                if (tag.getIsFilterable() == null) {
                } else if (tag.getIsFilterable()) {
                    filterTags.add(tag);
                }
            }
            model.addAttribute("tag", filterTags);

        //render found recipes
        model.addAttribute("categories", Category.values());
        model.addAttribute("category", category);
        model.addAttribute("sort", SortParameter.values());
        model.addAttribute("category", category);
        model.addAttribute("recipes", foundRecipes);


        return "search";
    }


    //SORTING
    @PostMapping(value = "/sort")
    public String sortSearchResults(@RequestParam SortParameter sortParameter, @RequestParam Category category, Model model) {
        List<Tag> tags = tagRepository.findAll();
        if(selectedTags.isEmpty()) {
            selectedTags = tags;
        }
        List<Tag> filterTags = new ArrayList<>();

        for (Tag tag : tags) {
            if(tag.getIsFilterable() == null){}
            else if (tag.getIsFilterable()) {
                filterTags.add(tag);
            }
        }

        model.addAttribute("tag", filterTags);

        Iterable<Recipe> recipes = foundRecipes;
        List<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            for (Tag recipeTag : recipe.getTags()) {
                for (Tag tag : selectedTags) {
                    if (recipeTag.getId() == tag.getId()) {
                        if (!filteredRecipes.contains(recipe)) {
                            filteredRecipes.add(recipe);
                        }
                    }
                }
            }
        }

        //If selected sort is NAME ASCENDING
        if ((sortParameter.getName().equals(SortParameter.NAME_ASCENDING.getName()))) {
            Collections.sort(filteredRecipes, new Recipe.SortByNameAsc());
            //If selected sort is NAME DESCENDING
        } else if ((sortParameter.getName().equals(SortParameter.NAME_DESCENDING.getName()))) {
            Collections.sort(filteredRecipes, new Recipe.SortByNameDesc());
            //if selected sort is ASCENDING RATING
        } else if ((sortParameter.getName().equals(SortParameter.RATING_ASCENDING.getName()))) {
            Collections.sort(filteredRecipes, new Recipe.SortByRatingAsc());
            //if selected sort is DESCENDING RATING
        } else if ((sortParameter.getName().equals(SortParameter.RATING_DESCENDING.getName()))) {
            Collections.sort(filteredRecipes, new Recipe.SortByRatingDsc());
        }
        //render found recipes
        model.addAttribute("recipes", filteredRecipes);
        model.addAttribute("categories", Category.values());
        model.addAttribute("category", category);
        model.addAttribute("sort", SortParameter.values());
        model.addAttribute("selectedTags", selectedTags);

        return "search";
    }




    @PostMapping(value = "/filter")
    public String filterResults(@RequestParam (required = false) List<Integer> tagId, @RequestParam (required=false) Category category, Model model) {
        model.addAttribute("category", category);
            List<Tag> allTags = tagRepository.findAll();
            selectedTags.clear();

            // store selected filters in an arrayList
            if(tagId != null) {
                for (Integer aTagId : tagId) {
                    selectedTags.add(tagRepository.findById(aTagId).get());
                }
            } else if( tagId == null) {
                selectedTags = allTags;
            }

            // filter checkboxes
            List<Tag> filters = new ArrayList<>();
            for (Tag aTag : allTags) {
                if (aTag.getIsFilterable() == null) {
                } else if (aTag.getIsFilterable()) {
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
                            if (!filteredRecipes.contains(recipe)) {
                                filteredRecipes.add(recipe);
                            }
                        }
                    }
                }
            }
            //render filtered recipes
            model.addAttribute("selectedTags", selectedTags);
            model.addAttribute("recipes", filteredRecipes);
            model.addAttribute("categories", Category.values());
            model.addAttribute("sort", SortParameter.values());


            return "search";
    }
}

