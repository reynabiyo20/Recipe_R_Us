package org.launchcode.recipeapp.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oksana
 */
@EqualsAndHashCode(callSuper = true)
@Entity
public class Tag extends AbstractEntity {
   @NotBlank(message = "Tag name required")
   String name;

   @ManyToMany(mappedBy="tags")
   List<Recipe> recipes = new ArrayList<>();

   public Tag(String name) {
      this.name = name;
   }

//   public List<Tag> filterableTags = new ArrayList<>();
//
//   public static boolean isFilterable;
//
//   public static void addToFilterableTags {
//      if()
//   }


   public Tag() { }


   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<Recipe> getRecipes() {
      return recipes;
   }

//   public boolean isFilterable() {
//      return isFilterable;
//   }
}
