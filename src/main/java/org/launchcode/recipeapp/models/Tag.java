package org.launchcode.recipeapp.models;

/**
 * @author Oksana
 */
public enum Tag {
   GLUTEN_FREE ("Gluten Free"),
   VEGETARIAN ("Vegetarian"),
   NUT_FREE ("Nut Free"),
   DAIRY_FREE ("Dairy Free"),
   MEAT ("Meat"),
   SEAFOOD ("Seafood");

   private String name;

   Tag(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

}
