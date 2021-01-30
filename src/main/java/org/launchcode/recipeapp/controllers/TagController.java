package org.launchcode.recipeapp.controllers;

import org.launchcode.recipeapp.models.Recipe;
import org.launchcode.recipeapp.models.Tag;
import org.launchcode.recipeapp.models.data.RecipeRepository;
import org.launchcode.recipeapp.models.data.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("recipes")
public class TagController {
    private final TagRepository tagRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public TagController(TagRepository tagRepository, RecipeRepository recipeRepository) {
        this.tagRepository = tagRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("tag")
    public String getAllRecipesByTag (Model model, @RequestParam int id){
        Tag tag = tagRepository.findById(id).get();
        List<Recipe> recipes = tag.getRecipes();
        model.addAttribute("tag", tag);
        model.addAttribute("recipes", recipes);
        return "recipes/tag";
    }

    @GetMapping("tags")
    public String displayCreateTag(Model model){
        Iterable<Tag> tags = tagRepository.findAll();
        model.addAttribute("tags", tags);
        model.addAttribute(new Tag());
        return "recipes/tags";
    }

    @PostMapping("tags")
    public String processCreateTag(@ModelAttribute @Valid Tag tag, Errors errors, Model model) {
        List<Tag> tags = tagRepository.findAll();

        if (!errors.hasErrors()) {
            Optional<Tag> tagOptional = tags.parallelStream()
                    .filter(t -> tag.getName().equals(t.getName()))
                    .findAny();
            if (tagOptional.isPresent()) {
                model.addAttribute("message", "This tag already exists");
                model.addAttribute("tags", tags);
                return "recipes/tags";
            } else {
                tagRepository.save(tag);
            }
        }

        return "redirect:/recipes/tags";
    }


//    @PostMapping("tags")
//    public String processCreateTag(@ModelAttribute @Valid Tag tag, Errors errors, Model model){
//        if(errors.hasErrors()) {
//            Iterable<Tag> tags = tagRepository.findAll();
//            model.addAttribute("tags", tags);
//            return "recipes/tags";
//        } else {
//            tagRepository.save(tag);
//        }
//        Iterable<Tag> tags = tagRepository.findAll();
//        model.addAttribute("tags", tags);
//        return "recipes/tags";
//
//    }

    @RequestMapping("tags/delete/{id}")
    public String deleteTag(@PathVariable Integer id) {
        Optional<Tag> tagOpt = tagRepository.findById(id);
        Iterable<Recipe> recipeOpt = recipeRepository.findAll();
        if (tagOpt.isPresent()) {
            tagRepository.deleteById(id);
        }
        return "redirect:/recipes/tags";
    }

    @GetMapping("edit-tag")
    public String renderEditTag(@RequestParam Integer id, Model model) {
        Optional<Tag> tagOpt = tagRepository.findById(id);
        if (tagOpt.isPresent()) {
            model.addAttribute("tag", tagOpt.get());
        } else{
            model.addAttribute("tag" , "Tag not found");
        }
        return "recipes/edit-tag";
    }

    @PostMapping("edit-tag")
    public String processEditTagForm(@RequestParam Integer id, @ModelAttribute @Valid Tag newTag, Errors errors, Model model) {
        Tag tag = tagRepository.findById(id).get();

        if(errors.hasErrors()) {
            model.addAttribute("tag", tag);
            System.out.println(errors);
            return "recipes/edit-tag";
        } else {
            tag.setName(newTag.getName());
            tagRepository.save(tag);
            model.addAttribute("tag", tag);
            return "redirect:/recipes/tags";
        }
    }
}
