package org.launchcode.recipeapp.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

   @ManyToMany(mappedBy="tags", fetch = FetchType.EAGER)
   List<Recipe> recipes = new ArrayList<>();


   Boolean isFilterable;


   public Tag(String name, Boolean isFilterable) {
      this.name = name;
      this.isFilterable = isFilterable;
   }

   public Tag() { }


   public String getName() {
//      return name.toLowerCase();
      return name;

   }

   public void setName(String name) {
      this.name = name;
   }

   public List<Recipe> getRecipes() {
      return recipes;
   }

   public Boolean getIsFilterable() {
      return isFilterable;
   }

   public void setFilterable(Boolean filterable) {
      isFilterable = filterable;
   }

   public String nameToLowerCase(){
      return name;
   }

}
