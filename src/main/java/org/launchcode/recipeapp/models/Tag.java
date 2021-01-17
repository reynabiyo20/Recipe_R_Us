package org.launchcode.recipeapp.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oksana
 */
@Entity
public class Tag extends AbstractEntity {
   @NotNull(message = "Tag name required")
   @NotBlank(message = "Tag name required")
   String name;

   @ManyToMany(mappedBy="tags")
   List<Recipe> recipes = new ArrayList<>();

   public Tag(String name) {
      this.name = name;
   }

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
}
